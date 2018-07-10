package cn.edu.shu.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-5 上午11:18.
 */
public class SocketChannelSender {

    private AsynchronousSocketChannel socketChannel;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void sendData() {
        try {
            this.socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(new InetSocketAddress(10002), null, new ClientWriteCompletionHandler(socketChannel));
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SocketChannelSender().sendData();
    }
}
