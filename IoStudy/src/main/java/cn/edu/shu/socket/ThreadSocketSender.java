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
public class ThreadSocketSender {
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("socket-demo-pool-%d").build();
    private ExecutorService executor = new ThreadPoolExecutor(10,
            20, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    public void sendData() {
        int max = 30;
        while (max-- > 0) {
            executor.submit(new StartSocket());
        }
    }

    public static void main(String[] args) {
        new ThreadSocketSender().sendData();
    }

    class StartSocket implements Runnable {
        @Override
        public void run() {
            OutputStream outputStream = null;
            Socket socket = null;
            try {
                socket = new Socket("localhost", 10000);
                outputStream = socket.getOutputStream();
                System.out.println(Thread.currentThread().getName() + " ready to send");
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.write("hello " + Thread.currentThread().getName());
                printWriter.write("eof");
                printWriter.flush();
                socket.shutdownOutput();
                InputStream inputStream = socket.getInputStream();
                final int bufferSize = 1024;
                final char[] buffer = new char[bufferSize];
                final StringBuilder out = new StringBuilder();
                Reader in = new InputStreamReader(inputStream, "UTF-8");
                int size;
                while ((size = in.read(buffer, 0, buffer.length)) != -1) {
                    out.append(buffer, 0, size);
                }
                System.out.println(Thread.currentThread().getName() + " accept " + out.toString() + " from server!");
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
    }
}
