package cn.edu.shu.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author jxxiangwen
 * mail: :jxxiangwen@live.com
 * Time: 18-1-6 下午9:59.
 */
public class ClientWriteCompletionHandler implements CompletionHandler<Void, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ClientWriteCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Void result, ByteBuffer attachment) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = "hello server".getBytes();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        channel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (byteBuffer.hasRemaining()) {
                    channel.write(byteBuffer, byteBuffer, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes1 = new byte[attachment.remaining()];
                            attachment.get(bytes1);
                            try {
                                String body = new String(bytes1, "UTF-8");
                                System.out.println("receive " + body + "from server !");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
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
