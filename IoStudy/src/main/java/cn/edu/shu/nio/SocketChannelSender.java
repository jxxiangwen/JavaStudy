package cn.edu.shu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-5 上午11:18.
 */
public class SocketChannelSender {

    private Selector selector;

    public void sendData() {
        Socket socket = null;
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            this.selector = Selector.open();
            doConnect(socketChannel);
            while (true) {
                while (selector.select(100) == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey readyKey = iterator.next();
                    iterator.remove();
                    handleInput(readyKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != selector) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                System.out.println("======client socket channel 建立连接=======");
                if (socketChannel.finishConnect()) {
                    socketChannel.register(this.selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                }
            }
            if (key.isReadable()) {
                handleRead(socketChannel);
                System.exit(0);
            }
        }
    }

    private void handleRead(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuilder stringBuilder = new StringBuilder();
        int read;
        while ((read = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
            //当然java nio框架本身也提供编解码方式
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            stringBuilder.append(new String(bytes, "UTF-8"));
            byteBuffer.clear();
        }
        System.out.println("receive " + stringBuilder.toString() + " from server !");
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "query timestamp over".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    private void doConnect(SocketChannel socketChannel) throws IOException {
        if (socketChannel.connect(new InetSocketAddress(10001))) {
            socketChannel.register(this.selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        }
    }

    public static void main(String[] args) {
        System.out.println("mall/goods////getGoodsList".replaceAll("//+","/"));
//        new SocketChannelSender().sendData();
    }
}
