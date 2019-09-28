package com.ctrip.flight.grpc4.impl;

import com.ctrip.flight.grpc4.PeopleServiceGrpc;
import com.ctrip.flight.grpc4.StreamRequest;
import com.ctrip.flight.grpc4.StreamResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

/**
 * Created by xuke
 * Description: 这里不能采用阻塞的方式来实现客户端了
 * Date: 2019-09-25
 * Time: 1:59
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost", 8899).
                usePlaintext(true).
                build();
        // 相比于PeopleServiceBlockingStub，PeopleServiceStub是异步的。
        PeopleServiceGrpc.PeopleServiceStub serviceStub = PeopleServiceGrpc.newStub(managedChannel);

        // 注意：只要客户端向服务器端是以流式的形式发送请求，这种情况下一定都是异步（BlockingStub是同步阻塞的形式）的。
        StreamObserver<StreamRequest> requestStreamObserver = serviceStub.biTalk(new StreamObserver<StreamResponse>() {
            // 下面的这些都是服务器端给返回来的数据
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            // 如果服务器端返回来的，表示服务器端那边一旦关闭了，客户端将信息打印出来就可以了
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }
        });

         // 为了模拟客户端向服务器端以流的形式将数据发送出去，我们可以写一个简单的循环
        for (int i = 0; i < 10; i++){
           requestStreamObserver.onNext(StreamRequest.newBuilder().
                   setRequestInfo(LocalDateTime.now().toString()).build());

           // 为了更清楚的看到这一点（区分开LocalDateTime.now()），我们每次让线程睡眠一段时间
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // 这个也是为了看到异步的效果加的。不过在这不加这段代码是可以看到效果的，和之前的grpc3还是有点区别的。
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
