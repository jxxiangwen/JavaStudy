package cn.edu.shu.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 负责对每一个socketChannel的数据获取事件进行监听。<p>
 * <p>
 * 重要的说明：一个socketchannel都会有一个独立工作的SocketChannelReadHandle对象（CompletionHandler接口的实现），
 * 其中又都将独享一个“文件状态标示”对象FileDescriptor、
 * 一个独立的由程序员定义的Buffer缓存（这里我们使用的是ByteBuffer）、
 * 所以不用担心在服务器端会出现“窜对象”这种情况，因为JAVA AIO框架已经帮您组织好了。<p>
 * <p>
 * 但是最重要的，用于生成channel的对象：AsynchronousChannelProvider是单例模式，无论在哪组socketchannel，
 * 对是一个对象引用（但这没关系，因为您不会直接操作这个AsynchronousChannelProvider对象）。
 *
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-6 下午9:59.
 */
public class ServerReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ServerReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("the server receive " + req + " from client!");
            String response = "the timestamp is " + System.currentTimeMillis();
            doWrite(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String response) {
        byte[] bytes = response.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    channel.write(byteBuffer, byteBuffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("send fail");
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("send fail");
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
