package cn.edu.shu.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-5 上午11:18.
 */
public class AsyncServerSocketChannelAccepter {
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void acceptConnection() {
        try {
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            this.asynchronousServerSocketChannel.bind(new InetSocketAddress(10002));
            doAccept();
            countDownLatch.await();
            if (null != asynchronousServerSocketChannel) {
                try {
                    asynchronousServerSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        this.asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }

    public static void main(String[] args) {
        new AsyncServerSocketChannelAccepter().acceptConnection();
    }
}
