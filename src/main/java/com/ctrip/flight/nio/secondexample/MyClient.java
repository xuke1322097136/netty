package com.ctrip.flight.nio.secondexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 18:11
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        // 客户端只需要一个事件循环组，因为它需要负责连接即可
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();// 服务器端是ServerBootstrap，客户端就是Bootstrap
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new MyClientInitializer());

            ChannelFuture channelFuture =  bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
