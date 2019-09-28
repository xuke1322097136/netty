package com.ctrip.flight.nio.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**服务器端的输出结果：
 * decode method invoked!
 * 可读字节数：8
 * /127.0.0.1:7587, 消息为：123456
 * method encode invoked!
 * 654321
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务器端接收到的长度：" + length);
        System.out.println("服务器端接收到的内容：" +  new String(content, Charset.forName("utf-8")));
        System.out.println("服务器端接受到的消息数量为：" + (++this.count));

        // 接收到消息之后向客户端回消息
        String responseMessage = UUID.randomUUID().toString();
        int responseLength = responseMessage.length();
        byte[] responseContent = responseMessage.getBytes();

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseLength);
        personProtocol.setContent(responseContent);

        channelHandlerContext.writeAndFlush(personProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
