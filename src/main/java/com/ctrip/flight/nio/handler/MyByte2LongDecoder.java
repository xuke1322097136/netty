package com.ctrip.flight.nio.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-06
 * Time: 22:55
 * 由于我们在网络中传输的数据都是字节形式的（ByteBuf），所以在这将字节转化为真正的类型的话，使用的是解码器。
 * Attention: 其实在日常的开发工作中，我们使用的比较多的还是ReplayingDecoder，其实ReplayingDecoder也是继承的ByteToMessageDecoder。
 *            因为它不需要缓冲中的数据够不够进行判断，ReplayingDecoder底层会帮助我们去判断，并且提供了状态管理的一切操作。
 *            详见MyByte2LongDecoder2这个类
 *
 *     当然，如果我们想把Long类型的转化为String类型的话，那这里继承的就是MessageToMessageDecoder<Long>(因为上一个handler的类型是Long类型的)
 *          然后通过out添加到输出中(out.add(String.valueOf(msg)))，然后下一个hander就是我们的MyServerHandler，所以SimpleChannelInboundHandler<String>
 *              因为String是上一个handler的结果类型，即MessageToMessageDecoder的子类，而且channelRead0方法里面的msg也是String类型的。
 */
public class MyByte2LongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param ctx：表示的是hander关联的handlerContex
     * @param in：表示的是读取数据的来源
     * @param out：表示的是解码之后把数据写到什么地方
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode method invoked!");

         System.out.println("可读字节数：" + in.readableBytes());

         // 由于Long是8个字节的，所以我们需要进行判断，如果不够8个字节的话，机会出错了，所以在这要进行判断。
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }

    }
}
