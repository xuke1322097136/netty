package com.ctrip.flight.grpc3.impl;

import com.ctrip.flight.grpc3.*;
import io.grpc.stub.StreamObserver;

/**
 * Created by xuke
 * Description: 这里测试的事第三种情况：客户端发送的是流式数据，服务器端返回的是一个单个对象/一个集合
 * Date: 2019-09-24
 * Time: 1:05
 */
public class PeopleServiceImpl extends PeopleServiceGrpc.PeopleServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端发送过来的信息" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());

        responseObserver.onCompleted();
    }

    @Override
    public void getPeoplesByAge(PeopleRequest request, StreamObserver<PeopleResponse> responseObserver) {
        System.out.println("接收到客户端发送过来的信息" + request.getAge());

        // 为了消除波浪线，在这把名字改了一下。
        responseObserver.onNext(PeopleResponse.newBuilder().setName("aaa").setAge(18).setCity("北京").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("bbb").setAge(19).setCity("上海 ").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("ccc").setAge(20).setCity("广州").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("ddd").setAge(21).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    // 可以看到这个方法和上面两个方法的区别，这个方法是有准确的返回类型的，并且参数不再是request了。
    // responseObserver仍然是用来作响应的。请求为什么作为返回类型了呢？其实在这使用的是一个回调。先new出来一个responseObserver
    // 接口的实例对象，实现里面的三个方法，当特定的事件产生的时候，对应的回调方法就会自动的得到执行，那么我们就可以在回调方法里
    // 拿到请求信息，然后将服务器端的响应信息给发送回去。
    @Override
    public StreamObserver<PeopleRequest> getPeoplesWrapperByAges(StreamObserver<PeopleResponseList> responseObserver) {
        // 这整个信息表示的是客户端的信息，实现的是StreamObserver<PeopleRequest>接口
        return new StreamObserver<PeopleRequest>() {

            // 可以看到这个方法的参数是PeopleRequest，每当客户端来一个请求的时候，该方法就会被调用一次，因为请求是一个流式的
            @Override
            public void onNext(PeopleRequest value) {
                System.out.println("onNext:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            // 表示客户端所有的信息一个一个地发送给服务器端之后，客户端就会触发onCompleted一个事件，服务器端感知到这个事件之后，
            // 它（responseObserver）会在它自己的onCompleted方法里面返回最终的响应结果（一个List）
            @Override
            public void onCompleted() {
                PeopleResponse peopleResponse = PeopleResponse.newBuilder().
                        setName("张三").setAge(30).setCity("纽约").build();
                PeopleResponse peopleResponse2 = PeopleResponse.newBuilder().
                        setName("李四").setAge(40).setCity("洛杉矶").build();

                PeopleResponseList responseList = PeopleResponseList.newBuilder().
                        addPeopleResponse(peopleResponse).addPeopleResponse(peopleResponse2).build();

                responseObserver.onNext(responseList);
                responseObserver.onCompleted();
            }
        };
    }
}
