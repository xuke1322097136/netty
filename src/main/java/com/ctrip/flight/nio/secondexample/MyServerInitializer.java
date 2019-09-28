package com.ctrip.flight.nio.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 17:45
 * MyServerInitializer的作用是往pipeline中添加一个一个的handler，最后添加完了之后，它会在程序运行的时候（addLast方法）中把自己删除掉
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();

        // 接下来往pipe里面添加pipeline，可以省去名字（即第一个参数）
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        // 自定义的处理器
        pipeline.addLast(new MyServerHandler());
    }
}
