package com.ctrip.flight.nio.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 17:59
 */
// 在这我们传输的是字符串，即客户端发送过来的是String请求对象，不是HttpRequest类型的了，所以泛型我们采用的是String
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
     // 打印出远程的地址（即客户端的地址）和发送过来的消息
        System.out.println(channelHandlerContext.channel().remoteAddress() + ", 消息为：" + s);
        channelHandlerContext.writeAndFlush("from server: " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
