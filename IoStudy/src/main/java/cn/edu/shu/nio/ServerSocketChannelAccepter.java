package cn.edu.shu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author jxxiangwen
 * mail: :xiangwen.zou@ymm56.com
 * Time: 18-1-5 上午11:18.
 */
public class ServerSocketChannelAccepter {

    public void acceptConnection() {
        ServerSocket serverSocket = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            // 创建server channel
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            // 生成socket
            serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(10001));
            Selector selector = Selector.open();
            // 绑定selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            try {
                while (true) {
                    // 如果条件成立，说明本次询问selector，并没有获取到任何准备好的、感兴趣的事件
                    // java程序对多路复用IO的支持也包括了阻塞模式 和非阻塞模式两种。
                    if (selector.select(100) == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey readyKey = iterator.next();
                        //这个已经处理的readyKey一定要移除。如果不移除，就会一直存在在selector.selectedKeys集合中
                        //待到下一次selector.select() > 0时，这个readyKey又会被处理一次
                        iterator.remove();
                        SelectableChannel selectableChannel = readyKey.channel();
                        if (readyKey.isValid()) {
                            if (readyKey.isAcceptable()) {
                                System.out.println("======channel通道已经准备好=======");
                                /*
                                 * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
                                 * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                                 * 否则无法监听到这个socket channel到达的数据
                                 * */
                                ServerSocketChannel serverChannel = (ServerSocketChannel) selectableChannel;
                                SocketChannel socketChannel = serverChannel.accept();
                                registerSocketChannel(socketChannel, selector);

                            } else if (readyKey.isConnectable()) {
                                System.out.println("======socket channel 建立连接=======");
                            } else if (readyKey.isReadable()) {
                                System.out.println("======socket channel 数据准备完成，可以去读==读取=======");
                                readSocketChannel(readyKey);
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocket.close();
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
            if (null != serverSocketChannel) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 在server socket channel接收到/准备好 一个新的 TCP连接后。
     * 就会向程序返回一个新的socketChannel。<br>
     * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
     * 所以程序还没法通过selector通知这个socket channel的事件。
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
     * socket channel感兴趣的事件
     *
     * @param socketChannel 新的socket channel
     * @param selector      selector“选择器/代理器”
     * @throws Exception
     */
    private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) throws IOException {
        socketChannel.configureBlocking(false);
        //socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
    }

    /**
     * 这个方法用于读取从客户端传来的信息。
     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输。
     * 如果传输完成，则返回一个true的标记。
     *
     * @param readyKey
     * @throws Exception
     */
    private static void readSocketChannel(SelectionKey readyKey) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) readyKey.channel();
        //获取客户端使用的端口
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
        Integer resoucePort = sourceSocketAddress.getPort();
        //拿到这个socket channel使用的缓存区，准备读取数据
        //在后文，将详细讲解缓存区的用法概念，实际上重要的就是三个元素capacity,position和limit。
        ByteBuffer byteBuffer = (ByteBuffer) readyKey.attachment();

        StringBuilder stringBuilder = new StringBuilder();
        int read;
        while ((read = clientSocketChannel.read(byteBuffer)) != 0) {
            byteBuffer.flip();
            //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
            //当然java nio框架本身也提供编解码方式
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            stringBuilder.append(new String(bytes,"UTF-8"));
            byteBuffer.clear();
        }

        String message = stringBuilder.toString();
        //如果收到了“over”关键字，才会清空buffer，并回发数据；
        //否则不清空缓存，还要还原buffer的“写状态”
        if (message.indexOf("over") != -1) {
            //清空已经读取的缓存，并从新切换为写状态(这里要注意clear()和capacity()两个方法的区别)
            byteBuffer.clear();
            System.out.println("端口:" + resoucePort + "客户端发来的信息======message : " + message);

            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了
            //======================================================

            //回发数据，并关闭channel
            byte[] bytes = ("now timestamp is " + System.currentTimeMillis()).getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            clientSocketChannel.write(buffer);
            clientSocketChannel.close();
        } else {
            System.out.println("端口:" + resoucePort + "客户端信息还未接受完，继续接受======message : " + message);
        }
    }

    public static void main(String[] args) {
        new ServerSocketChannelAccepter().acceptConnection();
    }
}
