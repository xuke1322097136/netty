package com.ctrip.flight.nio.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-07
 * Time: 23:54
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encoder method invoked!");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
