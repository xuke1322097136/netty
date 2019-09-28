package com.ctrip.flight.grpc.impl;

import com.ctrip.flight.grpc.MyRequest;
import com.ctrip.flight.grpc.MyResponse;
import com.ctrip.flight.grpc.PeopleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by xuke
 * Description: 客户端实现，同理可以参考
 * https://github.com/grpc/grpc-java/blob/v1.23.0/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java
 * Date: 2019-09-24
 * Time: 1:31
 */
public class GrpcClient {
    public static void main(String[] args) {
        // usePlaintext表示使用没有加密的方式来连接服务器端
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost", 8899).
                usePlaintext(true).
                build();
        // 连接服务器端的桩对象，代表客户端和服务器端真正交互的对象
        PeopleServiceGrpc.PeopleServiceBlockingStub blockingStub = PeopleServiceGrpc.
                newBlockingStub(managedChannel);
        // 传递的是MyRequest对象
        MyResponse myResponse = blockingStub.
                getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());
    }
}
