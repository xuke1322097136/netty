package com.ctrip.flight.grpc.impl;

import com.ctrip.flight.grpc.MyRequest;
import com.ctrip.flight.grpc.MyResponse;
import com.ctrip.flight.grpc.PeopleServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by xuke
 * Date: 2019-09-24
 * Time: 1:05
 * Description:
 *  1. impl文件里面的都是需要我们自己实现的，impl外面的那些文件都由插件生成，真正的服务处理对象。
 *      这个例子里面的People.proto里面只含有着一个方法及里面对应的message
 *              rpc GetRealNameByUsername(MyRequest) returns (MyResponse){}
 *   2. 自动生成代码，要从target/genertaed-sources/protobuf/...目录下剪切过来，直接复制过来的话有可能会出错。（如何不用拷贝呢？）
 *   3. 拷贝过来要把protobuf目录下面的文件都删了，不然会报重复类的错。
 *   4. 另外，我们可以看到自动生成的代码中,java目录下自动生成的都是消息message对应的内容，而grpc目录下的PeopleServiceGrpc文件
 *       是关于IDL文件中关于方法的内容。
 *    如何不用复制然后删除的方式来完成文件拷贝呢？
 *    1）可以考虑用subtree/submodular的方式；
 *    2）直接设置默认路径(target/generated-sources/protobuf/...)：
 *         a.) 通过两个参数（outputDirectory和clearOutputDirectory）进行设置。(详见pom.xml)
 *         b.)其实我们的proto文件的位置也是可以设置的，默认情况下，我们放置在src/main/proto文件夹下。

 */
public class PeopleServiceImpl extends PeopleServiceGrpc.PeopleServiceImplBase {
    /**
     * StreamObserver对象主要是用于向客户端响应结果的，同时标识getRealNameByUsername方法调用结束
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端发送过来的信息" + request.getUsername());

        // onNext()方法表示接下来要做的事情，在这个例子里是将结果返回给客户端。但是很多时候是用来处理Stream的相关情况
        // 返回的MyResponse对象是用protobuf的形式来构造的
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());

        responseObserver.onCompleted();// 表示这个方法已经调用结束
    }
}
