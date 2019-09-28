package com.ctrip.flight.grpc3.impl;

import com.ctrip.flight.grpc3.PeopleRequest;
import com.ctrip.flight.grpc3.PeopleResponseList;
import com.ctrip.flight.grpc3.PeopleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by xuke
 * Description: 这里不能采用阻塞的方式来实现客户端了
 * Date: 2019-09-25
 * Time: 0:50
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost", 8899).
                usePlaintext(true).
                build();
        // 相比于PeopleServiceBlockingStub，PeopleServiceStub是异步的。
        PeopleServiceGrpc.PeopleServiceStub serviceStub = PeopleServiceGrpc.newStub(managedChannel);

        // 其实这里跟服务器端的业务处理（PeopleServiceImpl）是正好相反的。这里表示的是服务器端给客户端返回来的数据之后，
        // 客户端会在特定的方法上被回调。
        StreamObserver<PeopleResponseList> peopleResponseListStreamObserver = new StreamObserver<PeopleResponseList>() {

            // 服务器端真正给客户端返回结果的时候，客户端的onNext方法就会被触发和调用
            @Override
            public void onNext(PeopleResponseList value) {
             value.getPeopleResponseList().forEach(peopleResponse -> {
                 System.out.println(peopleResponse.getName());
                 System.out.println(peopleResponse.getAge());
                 System.out.println(peopleResponse.getCity());
                 System.out.println("******************");
             });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed!");
            }
        };

        // 接下来构造客户端给服务器端最开始发送的流式数据（多个onNext方法来构造）。
        // 注意：只要客户端向服务器端是以流式的形式发送请求，这种情况下一定都是异步（BlockingStub是同步阻塞的形式）的。
        // 可以看到PeopleServiceGrpc里面的三个方法都可以通过异步的形式来进行调用
        StreamObserver<PeopleRequest> peopleRequestStreamObserver = serviceStub.
                getPeoplesWrapperByAges(peopleResponseListStreamObserver);

        peopleRequestStreamObserver.onNext(PeopleRequest.newBuilder().setAge(11).build());
        peopleRequestStreamObserver.onNext(PeopleRequest.newBuilder().setAge(22).build());
        peopleRequestStreamObserver.onNext(PeopleRequest.newBuilder().setAge(33).build());
        peopleRequestStreamObserver.onNext(PeopleRequest.newBuilder().setAge(44).build());

        peopleRequestStreamObserver.onCompleted();

        // 正常情况下，启动服务器端和客户端是没有输出结果的。这是因为serviceStub调用的getPeoplesWrapperByAges方法是异步调用的，
        // 还没等到返回结果peopleRequestStreamObserver纠结着往下执行了，直到最后的onCompleted方法调用完了还没有结果输出。
        // 为了可以看到正常的输出结果，我们可以让线程休眠一段时间
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
