package com.ctrip.flight.nio.secondexample;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 18:24
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    // 该方法表示服务器端向客户端发送消息的时候，会被调用。和MyServerHandler里面的channelRead0方法正好相反
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("服务器端的地址为：" + channelHandlerContext.channel().remoteAddress());
        System.out.println("服务器端向客户端发送的消息为：" + s);
        channelHandlerContext.writeAndFlush("from client: " + LocalDateTime.now());// 给服务器端回传一个时间
    }

    /**
     *      第一次刚建立连接的时候会调用该方法，也就是说，该方法的执行时机要先于channelRead0方法。
     *      客户端和服务器端建立好连接之后，并且channel处于活动状态了，该方法会立刻被调用,表示客户端可以向服务器端发送消息了。
     *      如果没有这个方法，我们是看不到任何输出结果的，因为客户端和服务器端都没发送过消息，因此我们可以通过该回调方法来发送消息
     *      不要在ChannelHandler的这些方法里调用await（）方法，不然会造成是死锁，具体原因在ChannelFuture的源码里有解释。
     *              ChannelHandler（负责完成IO操作）   -------->     调用await()方法
     *               而await()方法又会阻塞等待着IO操作的完成，构成了一个环，所以会造成死锁。
     *      取而代之，我们可以利用
     *      addListener的方法来实现future.addListener(new {@link ChannelFutureListener
     *           }() {
     *              public void operationComplete({@link ChannelFuture
     *              } future) {
     *                   Perform post-closure operation
     *                    ...
     *             }
     *          });
      */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active");
        ctx.writeAndFlush("来自于客户端的问候!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
