package cn.edu.shu.socket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-5 上午8:47.
 */
public class SocketSender {

    public void sendData() {
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10000);
            outputStream = socket.getOutputStream();
            System.out.println(" ready to send");
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            printWriter.write("hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            //eof为结束标记
            printWriter.write("eof");
            printWriter.flush();
            InputStream inputStream = socket.getInputStream();
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(inputStream, "UTF-8");
            int size;
            while ((size = in.read(buffer, 0, buffer.length)) != -1) {
                out.append(buffer, 0, size);
            }
            System.out.println(" accept " + out.toString() + " from server!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
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

    public static void main(String[] args) {
        new SocketSender().sendData();
    }
}
