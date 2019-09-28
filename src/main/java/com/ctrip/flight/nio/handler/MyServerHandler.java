package com.ctrip.flight.nio.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**服务器端的输出结果：
 * decode method invoked!
 * 可读字节数：8
 * /127.0.0.1:7587, 消息为：123456
 * method encode invoked!
 * 654321
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + ", 消息为：" + s);

        // 服务器端向客户端回一个Long类型的数据
        channelHandlerContext.writeAndFlush(654321L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
