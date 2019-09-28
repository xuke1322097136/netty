package com.ctrip.flight.nio.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {

         // 可以看到客户端传递过来的10条消息都存到了ByteBuf中，即所有的消息都粘在一起了。
          byte[] buffer = new byte[msg.readableBytes()];
          msg.readBytes(buffer);// 数据读取到buffer里面

        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端接收到的数据：" + message);
        System.out.println("接收到的消息数量为：" + (++this.count));

        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        channelHandlerContext.writeAndFlush(responseByteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
