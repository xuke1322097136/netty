package com.ctrip.flight.nio.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 0:03
 */

// 客户端一旦跟服务器端建立起连接之后，MyServerInitializer就会被创建并且initChannel方法就会被调用
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();

        // 注意这些handler不要搞成单例的，保证每次都能new出来一个新的实例
        pipeline.addLast("httpServerCodec", new HttpServerCodec());// 请求外面的编解码使用的，包含HttpRequestDecoder和HttpResponseEncoder
        pipeline.addLast("httpServerHandler", new MyHttpServerHandler());// 我们自己定义的handler
    }
}
