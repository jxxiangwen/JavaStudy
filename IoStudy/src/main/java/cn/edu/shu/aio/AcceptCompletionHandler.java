package cn.edu.shu.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author jxxiangwen
 * mail: :xiangwen.zou@ymm56.com
 * Time: 18-1-6 下午9:59.
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerSocketChannelAccepter> {
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncServerSocketChannelAccepter attachment) {
        //每次都要重新注册监听（一次注册，一次响应），但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer, byteBuffer, new ServerReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncServerSocketChannelAccepter attachment) {
        exc.printStackTrace();
    }
}
