package com.ctrip.flight.nio.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();

        // 自定义的处理器
//        pipeline.addLast(new MyByte2LongDecoder());
        pipeline.addLast(new MyByte2LongDecoder2());
        pipeline.addLast(new MyLong2ByteEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}
