package com.ctrip.flight.nio.handler;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-04
 * Time: 16:19
 *
 *  Netty处理器重要概念：
 *   1. Netty处理器可以分为两类：入站处理器和出站处理器；
 *   2. 入站处理器的最顶层为ChannelInboundHandler，出站处理器的最顶层为ChannelOutboundHandler；
 *   3. 数据处理时的各种编解码器其实都是处理器handler；
 *   4. 编解码器：无论我们往网络中写的数据是什么类型（int, char, String，二进制等），数据在网络中传输时，都是以字节流的形式呈现的，
 *                将数据由原本(int、long、String等)的形式转换为字节流（ByteBuf）的操作称作为编码(encode)，将数据由字节转换为它原本的格式
 *                或者是其他格式的操作称之为解码(decode)，编解码统称为codec；
 *    5. 编码(encode)：本质上是一个出站处理器，因此编码一定是一种ChannelOutboundHandler；
 *    6. 解码(decode)：本质上是一个入站处理器，因此编码一定是一种ChannelInboundHandler；
 *    7. 在Netty中，编码通常以XXXEncoder形式命名，解码通常以XXXDecoder形式命名。
 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.WARN)).
                    childHandler(new MyServerInitializer());

            ChannelFuture channelFuture =  serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
