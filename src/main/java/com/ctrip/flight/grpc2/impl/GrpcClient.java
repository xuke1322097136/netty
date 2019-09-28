package com.ctrip.flight.grpc2.impl;


import com.ctrip.flight.grpc2.PeopleRequest;
import com.ctrip.flight.grpc2.PeopleResponse;
import com.ctrip.flight.grpc2.PeopleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * Created by xuke
 * Description:  在这我们发送给服务器端的是一个年龄值（用PeopleRequest包装的）,返回的是Iterator<PeopleResponse>
 * Date: 2019-09-25
 * Time: 0:06
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost", 8899).
                usePlaintext(true).
                build();
        PeopleServiceGrpc.PeopleServiceBlockingStub blockingStub = PeopleServiceGrpc.
                newBlockingStub(managedChannel);

        // 服务器端返回的其实就是一个Iterator，具体可以在自动生成的PeopleServiceGrpc就可以看到具体的返回值
        Iterator<PeopleResponse> iterator = blockingStub.
                getPeoplesByAge(PeopleRequest.newBuilder().setAge(20).build());

        while (iterator.hasNext()){
           PeopleResponse response =  iterator.next();
            System.out.println(response.getName() + "," + response.getAge() + "," + response.getCity());
        }
        System.out.println("..............");
    }
}
