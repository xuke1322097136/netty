package com.ctrip.flight.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *    rpc关键字来指定这是一个rpc调用，方法名的第一个字母无论你是大写还是小写，最后生成的代码都会转化成小写的开头
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: People.proto")
public final class PeopleServiceGrpc {

  private PeopleServiceGrpc() {}

  public static final String SERVICE_NAME = "com.ctrip.flight.grpc.PeopleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.ctrip.flight.grpc.MyRequest,
      com.ctrip.flight.grpc.MyResponse> getGetRealNameByUsernameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRealNameByUsername",
      requestType = com.ctrip.flight.grpc.MyRequest.class,
      responseType = com.ctrip.flight.grpc.MyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ctrip.flight.grpc.MyRequest,
      com.ctrip.flight.grpc.MyResponse> getGetRealNameByUsernameMethod() {
    io.grpc.MethodDescriptor<com.ctrip.flight.grpc.MyRequest, com.ctrip.flight.grpc.MyResponse> getGetRealNameByUsernameMethod;
    if ((getGetRealNameByUsernameMethod = PeopleServiceGrpc.getGetRealNameByUsernameMethod) == null) {
      synchronized (PeopleServiceGrpc.class) {
        if ((getGetRealNameByUsernameMethod = PeopleServiceGrpc.getGetRealNameByUsernameMethod) == null) {
          PeopleServiceGrpc.getGetRealNameByUsernameMethod = getGetRealNameByUsernameMethod =
              io.grpc.MethodDescriptor.<com.ctrip.flight.grpc.MyRequest, com.ctrip.flight.grpc.MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRealNameByUsername"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc.MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc.MyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleServiceMethodDescriptorSupplier("GetRealNameByUsername"))
              .build();
        }
      }
    }
    return getGetRealNameByUsernameMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PeopleServiceStub newStub(io.grpc.Channel channel) {
    return new PeopleServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PeopleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PeopleServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PeopleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PeopleServiceFutureStub(channel);
  }

  /**
   * <pre>
   *    rpc关键字来指定这是一个rpc调用，方法名的第一个字母无论你是大写还是小写，最后生成的代码都会转化成小写的开头
   * </pre>
   */
  public static abstract class PeopleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRealNameByUsername(com.ctrip.flight.grpc.MyRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc.MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRealNameByUsernameMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetRealNameByUsernameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ctrip.flight.grpc.MyRequest,
                com.ctrip.flight.grpc.MyResponse>(
                  this, METHODID_GET_REAL_NAME_BY_USERNAME)))
          .build();
    }
  }

  /**
   * <pre>
   *    rpc关键字来指定这是一个rpc调用，方法名的第一个字母无论你是大写还是小写，最后生成的代码都会转化成小写的开头
   * </pre>
   */
  public static final class PeopleServiceStub extends io.grpc.stub.AbstractStub<PeopleServiceStub> {
    private PeopleServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PeopleServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PeopleServiceStub(channel, callOptions);
    }

    /**
     */
    public void getRealNameByUsername(com.ctrip.flight.grpc.MyRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc.MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *    rpc关键字来指定这是一个rpc调用，方法名的第一个字母无论你是大写还是小写，最后生成的代码都会转化成小写的开头
   * </pre>
   */
  public static final class PeopleServiceBlockingStub extends io.grpc.stub.AbstractStub<PeopleServiceBlockingStub> {
    private PeopleServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PeopleServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PeopleServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.ctrip.flight.grpc.MyResponse getRealNameByUsername(com.ctrip.flight.grpc.MyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRealNameByUsernameMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *    rpc关键字来指定这是一个rpc调用，方法名的第一个字母无论你是大写还是小写，最后生成的代码都会转化成小写的开头
   * </pre>
   */
  public static final class PeopleServiceFutureStub extends io.grpc.stub.AbstractStub<PeopleServiceFutureStub> {
    private PeopleServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PeopleServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PeopleServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PeopleServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.ctrip.flight.grpc.MyResponse> getRealNameByUsername(
        com.ctrip.flight.grpc.MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_REAL_NAME_BY_USERNAME = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PeopleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PeopleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_REAL_NAME_BY_USERNAME:
          serviceImpl.getRealNameByUsername((com.ctrip.flight.grpc.MyRequest) request,
              (io.grpc.stub.StreamObserver<com.ctrip.flight.grpc.MyResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PeopleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PeopleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.ctrip.flight.grpc.PeopleProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PeopleService");
    }
  }

  private static final class PeopleServiceFileDescriptorSupplier
      extends PeopleServiceBaseDescriptorSupplier {
    PeopleServiceFileDescriptorSupplier() {}
  }

  private static final class PeopleServiceMethodDescriptorSupplier
      extends PeopleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PeopleServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PeopleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PeopleServiceFileDescriptorSupplier())
              .addMethod(getGetRealNameByUsernameMethod())
              .build();
        }
      }
    }
    return result;
  }
}
