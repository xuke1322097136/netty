package com.ctrip.flight.nio.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    // 快捷键：shift+ctrl+pgup（向上的箭头）：代码自动向上移动
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

       for (int i = 0; i < 10; i++) {
         ByteBuf buffer = Unpooled.copiedBuffer("sent from client:" , Charset.forName("utf-8"));
         ctx.writeAndFlush(buffer);
       }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
       byte[] buffer = new byte[msg.readableBytes()];
         msg.readBytes(buffer);

         String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("服务器端向客户端发送的消息为：" + message);
        System.out.println("客户端接受到的数据条数有：" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
