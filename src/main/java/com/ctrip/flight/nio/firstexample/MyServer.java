package com.ctrip.flight.nio.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-20
 * Time: 14:45
 * https://blog.csdn.net/chen_xi_hao/article/details/79431756
 */
/**
 * 测试代码的时候我们开启MyServer的代码，然后在cmd中输入curl "http://localhost:8899"
 */
public class MyServer {
    public static void main(String[] args) {
        // 创建两个NioEventLoopGroup线程组用于不断地监听客户端，都是死循环，永远不会退出的
        // bossGroup线程组用于不断的接受客户端的连接，但是不做任何的处理。
        // bossGroup接收到客户端的连接请求之后，转给workerGroup线程组，由它们完成连接之后的处理。
        EventLoopGroup bossGroup  = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
      try {
          // 使用ServerBootstrap类来简化我们启动器,handler()方法的对应的是bossGroup，我们在这省略了，
          // 而childHandler（）方法对应的是workerGroup
          ServerBootstrap serverBootstrap = new ServerBootstrap();
          serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                  childHandler(new MyServerInitializer());// 初始化器，在channel一旦被创建好之后MyServerInitializer就会得到创建，就会调用里面的initChannel方法
         ChannelFuture channelFuture =  serverBootstrap.bind(8899).sync();
         channelFuture.channel().closeFuture().sync();
      } catch (InterruptedException e) {
          e.printStackTrace();
      } finally {
          // 优雅关闭两个时间循环组
         bossGroup.shutdownGracefully();
         workerGroup.shutdownGracefully();
      }

    }
}
