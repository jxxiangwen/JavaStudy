package cn.edu.shu.socket;

import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author jxxiangwen
 * mail: :xiangwen.zou@ymm56.com
 * Time: 18-1-5 上午8:47.
 */
public class ServerSocketAccepter {

    public void acceptConnection() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10000);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = socket.getInputStream();
                    System.out.println(" server ready to receive");
                    final int bufferSize = 1024;
                    final char[] buffer = new char[bufferSize];
                    final StringBuilder out = new StringBuilder();
                    Reader in = new InputStreamReader(inputStream, "UTF-8");
                    int size;
                    while ((size = in.read(buffer, 0, buffer.length)) != -1) {
                        out.append(buffer, 0, size);
                        // 必须要跳出，不然除非缓冲区填满或者
                        if (out.indexOf("eof") != -1) {
                            //判断结束标记
                            break;
                        }
                    }
                    System.out.println(" receive " + out.toString() + " from client!");
                    outputStream = socket.getOutputStream();
                    System.out.println(" ready to send!");
                    outputStream.write(("now timestamp is " + System.currentTimeMillis()).getBytes("UTF-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != inputStream) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != outputStream) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocketAccepter sender = new ServerSocketAccepter();
        sender.acceptConnection();
    }
}
