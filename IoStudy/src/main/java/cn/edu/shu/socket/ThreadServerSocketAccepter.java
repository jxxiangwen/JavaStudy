package cn.edu.shu.socket;

import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-5 上午8:47.
 */
public class ThreadServerSocketAccepter {
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("server-socket-demo-pool-%d").build();
    private ExecutorService executor = new ThreadPoolExecutor(10,
            20, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    public void acceptConnection() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10000);
            while (true) {
                Socket accept = serverSocket.accept();
                executor.submit(new SocketProcess(accept));
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
        ThreadServerSocketAccepter sender = new ThreadServerSocketAccepter();
        sender.acceptConnection();
    }


    class SocketProcess implements Runnable {
        private Socket socket;

        public SocketProcess(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = socket.getInputStream();
                System.out.println(Thread.currentThread().getName() + " server ready to receive");
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
                System.out.println(Thread.currentThread().getName() + " receive " + out.toString() + " from client!");
                outputStream = socket.getOutputStream();
                System.out.println(Thread.currentThread().getName() + " ready to send!");
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
    }

}
