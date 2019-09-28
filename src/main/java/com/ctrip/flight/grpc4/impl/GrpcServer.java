package com.ctrip.flight.grpc4.impl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by xuke
 * Description: Grpc服务器端的代码基本不需要改动.其实，流式数据其实就可以理解为一个迭代器或者是集合。
 *              服务器端向客户端返回数据的时候，是先返回一个对象，接着在返回一个对象。最后调用onCompleted标识虽有的结果都返回完毕。
 * Date: 2019-09-24
 * Time: 23:41
 */
public class GrpcServer {
    private Server server;

    // 启动服务器的方法
    private void start() throws IOException {
       this.server =  ServerBuilder.forPort(8899).addService(new PeopleServiceImpl()).build().start();
        System.out.println("server started!");

        // 通过回调钩子的方式关闭连接。在服务器端真正的JVM退出之前，让grpc连接就主动关闭掉，在关闭的同时将资源也给释放掉。
        // 否则有可能JVM已经关闭了，而socket还没有关闭，处于没法关闭的状态。
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("shutting down gRPC server since JVM is shutting down");
            GrpcServer.this.stop();
            System.out.println("server shut down!");
        }));

        System.out.println("执行到这里！");
    }

    // 关闭服务器的方法
    private void stop(){
        if (null != this.server){
            this.server.shutdown();
        }
    }

    //  grpc和thrift的不同之处在于：thrift在服务器端启动之后就阻塞住了，而grpc必须想办法让它阻塞，不能让他启动之后就退出了
    private void awaitTermination() throws Exception{
        if (null != this.server){
//            this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        GrpcServer grpcServer = new GrpcServer();

        grpcServer.start();
        grpcServer.awaitTermination();// 不加上这一行服务器在启动之后就自动退出了
    }

}
