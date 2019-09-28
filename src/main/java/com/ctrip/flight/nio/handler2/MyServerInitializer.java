package com.ctrip.flight.nio.handler2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    // 没建立一次连接该方法都会被调用
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println(this);
        ChannelPipeline pipeline =  socketChannel.pipeline();

        pipeline.addLast(new MyServerHandler());
    }
}
