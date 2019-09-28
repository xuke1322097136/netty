package com.ctrip.flight.nio.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-06
 * Time: 23:03
 * 为什么MessageToByteEncoder是指定了泛型I的，而ByteToMessageDecoder却是没有指定泛型的？
 * Encoder将I类型转化为ByteBuf类型，而ByteToMessageDecoder的结果是存储到List类型的out变量中的，而List里面的类型我们指定的是泛型的
 *
 *
 */
public class MyLong2ByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("method encode invoked!");

        System.out.println(msg);

        // 将数据写到ByteBuf里面
        out.writeLong(msg);
    }
}
