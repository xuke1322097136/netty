package com.ctrip.flight.grpc2.impl;


import com.ctrip.flight.grpc2.*;
import io.grpc.stub.StreamObserver;

/**
 * Created by xuke
 * Description: impl文件里面的都是需要我们自己实现的，impl外面的那些文件都由插件生成
 *              真正的服务处理对象。这个例子里面的People2.proto里面的方法和message.
 *              服务器端的业务实现。
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

        responseObserver.onNext(PeopleResponse.newBuilder().setName("张三").setAge(18).setCity("北京").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("李四").setAge(19).setCity("上海 ").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("王五").setAge(20).setCity("广州").build());
        responseObserver.onNext(PeopleResponse.newBuilder().setName("赵六").setAge(21).setCity("深圳").build());

        responseObserver.onCompleted();
    }
}
