package com.ctrip.flight.nio.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 0:13
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if(httpObject instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)httpObject;

            System.out.println("请求的方法名为：" + httpRequest.method().name());// GET/POST/PUT/DELETE

            URI uri = new URI(httpRequest.uri());
            // Chrome浏览器左上角对每个网页都有个图标，那个就是favicon.ico
            if ("/favicon.ico".equals(uri)){
                System.out.println("请求favicon.ico图标");
                return;
            }
          ByteBuf content =  Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
          // netty专门支撑响应的对象，构造出DefaultFullHttpResponse 包含协议版本，响应状态码和真正的响应内容
         FullHttpResponse response =  new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

         // 设置响应相关的一些头信息
         response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
         response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

         // 将Hello World返回给客户端，单独调用write()方法并不会返回客户端，它是将响应放入缓存中
            // 而writeAndFlush可以响应回客户端
         channelHandlerContext.writeAndFlush(response);
        }
    }
}
