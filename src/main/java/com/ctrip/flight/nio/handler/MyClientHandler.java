package com.ctrip.flight.nio.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**客户端的输出结果：
 * method encode invoked!
 * 123456
 * decode method invoked!
 * 8
 * 服务器端的地址为：localhost/127.0.0.1:8899
 * 服务器端向客户端发送的消息为：654321
 */

/**
 * 整个调用过程：客户端和服务器端建立好连接之后，客户端的channelActive方法被调用，然后将123456L进行编码，调用encode()方法，写到ByteBuf中
 *               然后传到服务器端，服务器端接收到之后ByteBuf进行解码，然后调用channelRead0方法。
 *               接着服务器端向客户端回写654321L，又进行编码，调用调用encode()方法，写到ByteBuf中，然后回传到客户端，客户端进行解码，
 *               然后调用channelRead0方法。
 *   注意：我们在这只对Long类型的变量进行了编解码处理，其他的类型，Netty会自动过滤掉不予以处理，也不会报错，是直接跳过的。
 *       具体可以参见MessageToByteEncoder里面的write方法，可以尝试将Long改成int并且在write方法里的  acceptOutboundMessage(msg) 这里打个断点
 *
 *       关于Netty编解码器的总结：
 *       1. 无论编码器还是解码器，其所接受的消息类型必须要与待处理的参数类型一致，否则该编码器或者解码器并不会被执行。即，下面直接传输的ByteBuf不会被encode。
 *       2. 在解码器对数据进行解码的时候，一定要对缓冲（ByteBuf）中的数据进行判断，不然很有可能会出错。对应的是MyByte2LongDecoder里面的decode方法。
 *
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    // 该方法表示服务器端向客户端发送消息的时候，会被调用。和MyServerHandler里面的channelRead0方法正好相反
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long s) throws Exception {
        System.out.println("服务器端的地址为：" + channelHandlerContext.channel().remoteAddress());
        System.out.println("服务器端向客户端发送的消息为：" + s);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端向服务器端发送一个Long类型的数据
        ctx.writeAndFlush(123456L);
        // 可以看到下面这种形式也是能发送出去的，Netty是接受直接传输一种ByteBuf类型的数据的，但是由于没有适当的编码器，所以直接跳过encode()方法
        // 下一个环节就到了socket了，然后直接发送给了服务器端，服务器端然后回发消息。
       // ctx.writeAndFlush(Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
