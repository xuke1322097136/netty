package com.ctrip.flight.nio.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    // 快捷键：shift+ctrl+pgup（向上的箭头）：代码自动向上移动
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            String messageToBeSent = "sent from client: ";
            byte[] content = messageToBeSent.getBytes(CharsetUtil.UTF_8);

            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(messageToBeSent.length());
            personProtocol.setContent(content);

            ctx.writeAndFlush(personProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的消息长度：" + length);
        System.out.println("客户端接收到的消息内容：" + new String(content, Charset.forName("utf-8")));

        System.out.println("客户端接收到的消息数量： " + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
