package com.ctrip.flight.grpc3;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.*;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.*;

/**
 * <pre>
 *第三种情况：向服务器端发送一个PeopleRequest，返回的是一个PeopleResponseList(即多个PeopleResponse)
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: people3.proto")
public final class PeopleServiceGrpc {

  private PeopleServiceGrpc() {}

  public static final String SERVICE_NAME = "com.ctrip.flight.grpc3.PeopleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.MyRequest,
      com.ctrip.flight.grpc3.MyResponse> getGetRealNameByUsernameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRealNameByUsername",
      requestType = com.ctrip.flight.grpc3.MyRequest.class,
      responseType = com.ctrip.flight.grpc3.MyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.MyRequest,
      com.ctrip.flight.grpc3.MyResponse> getGetRealNameByUsernameMethod() {
    io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.MyRequest, com.ctrip.flight.grpc3.MyResponse> getGetRealNameByUsernameMethod;
    if ((getGetRealNameByUsernameMethod = PeopleServiceGrpc.getGetRealNameByUsernameMethod) == null) {
      synchronized (PeopleServiceGrpc.class) {
        if ((getGetRealNameByUsernameMethod = PeopleServiceGrpc.getGetRealNameByUsernameMethod) == null) {
          PeopleServiceGrpc.getGetRealNameByUsernameMethod = getGetRealNameByUsernameMethod =
              io.grpc.MethodDescriptor.<com.ctrip.flight.grpc3.MyRequest, com.ctrip.flight.grpc3.MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRealNameByUsername"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.MyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleServiceMethodDescriptorSupplier("GetRealNameByUsername"))
              .build();
        }
      }
    }
    return getGetRealNameByUsernameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest,
      com.ctrip.flight.grpc3.PeopleResponse> getGetPeoplesByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPeoplesByAge",
      requestType = com.ctrip.flight.grpc3.PeopleRequest.class,
      responseType = com.ctrip.flight.grpc3.PeopleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest,
      com.ctrip.flight.grpc3.PeopleResponse> getGetPeoplesByAgeMethod() {
    io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest, com.ctrip.flight.grpc3.PeopleResponse> getGetPeoplesByAgeMethod;
    if ((getGetPeoplesByAgeMethod = PeopleServiceGrpc.getGetPeoplesByAgeMethod) == null) {
      synchronized (PeopleServiceGrpc.class) {
        if ((getGetPeoplesByAgeMethod = PeopleServiceGrpc.getGetPeoplesByAgeMethod) == null) {
          PeopleServiceGrpc.getGetPeoplesByAgeMethod = getGetPeoplesByAgeMethod =
              io.grpc.MethodDescriptor.<com.ctrip.flight.grpc3.PeopleRequest, com.ctrip.flight.grpc3.PeopleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPeoplesByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.PeopleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.PeopleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleServiceMethodDescriptorSupplier("GetPeoplesByAge"))
              .build();
        }
      }
    }
    return getGetPeoplesByAgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest,
      com.ctrip.flight.grpc3.PeopleResponseList> getGetPeoplesWrapperByAgesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPeoplesWrapperByAges",
      requestType = com.ctrip.flight.grpc3.PeopleRequest.class,
      responseType = com.ctrip.flight.grpc3.PeopleResponseList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest,
      com.ctrip.flight.grpc3.PeopleResponseList> getGetPeoplesWrapperByAgesMethod() {
    io.grpc.MethodDescriptor<com.ctrip.flight.grpc3.PeopleRequest, com.ctrip.flight.grpc3.PeopleResponseList> getGetPeoplesWrapperByAgesMethod;
    if ((getGetPeoplesWrapperByAgesMethod = PeopleServiceGrpc.getGetPeoplesWrapperByAgesMethod) == null) {
      synchronized (PeopleServiceGrpc.class) {
        if ((getGetPeoplesWrapperByAgesMethod = PeopleServiceGrpc.getGetPeoplesWrapperByAgesMethod) == null) {
          PeopleServiceGrpc.getGetPeoplesWrapperByAgesMethod = getGetPeoplesWrapperByAgesMethod =
              io.grpc.MethodDescriptor.<com.ctrip.flight.grpc3.PeopleRequest, com.ctrip.flight.grpc3.PeopleResponseList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPeoplesWrapperByAges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.PeopleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.ctrip.flight.grpc3.PeopleResponseList.getDefaultInstance()))
              .setSchemaDescriptor(new PeopleServiceMethodDescriptorSupplier("GetPeoplesWrapperByAges"))
              .build();
        }
      }
    }
    return getGetPeoplesWrapperByAgesMethod;
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
   *第三种情况：向服务器端发送一个PeopleRequest，返回的是一个PeopleResponseList(即多个PeopleResponse)
   * </pre>
   */
  public static abstract class PeopleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRealNameByUsername(com.ctrip.flight.grpc3.MyRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRealNameByUsernameMethod(), responseObserver);
    }

    /**
     */
    public void getPeoplesByAge(com.ctrip.flight.grpc3.PeopleRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetPeoplesByAgeMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleRequest> getPeoplesWrapperByAges(
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponseList> responseObserver) {
      return asyncUnimplementedStreamingCall(getGetPeoplesWrapperByAgesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetRealNameByUsernameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.ctrip.flight.grpc3.MyRequest,
                com.ctrip.flight.grpc3.MyResponse>(
                  this, METHODID_GET_REAL_NAME_BY_USERNAME)))
          .addMethod(
            getGetPeoplesByAgeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.ctrip.flight.grpc3.PeopleRequest,
                com.ctrip.flight.grpc3.PeopleResponse>(
                  this, METHODID_GET_PEOPLES_BY_AGE)))
          .addMethod(
            getGetPeoplesWrapperByAgesMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                com.ctrip.flight.grpc3.PeopleRequest,
                com.ctrip.flight.grpc3.PeopleResponseList>(
                  this, METHODID_GET_PEOPLES_WRAPPER_BY_AGES)))
          .build();
    }
  }

  /**
   * <pre>
   *第三种情况：向服务器端发送一个PeopleRequest，返回的是一个PeopleResponseList(即多个PeopleResponse)
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
    public void getRealNameByUsername(com.ctrip.flight.grpc3.MyRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPeoplesByAge(com.ctrip.flight.grpc3.PeopleRequest request,
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetPeoplesByAgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleRequest> getPeoplesWrapperByAges(
        io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponseList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getGetPeoplesWrapperByAgesMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   *第三种情况：向服务器端发送一个PeopleRequest，返回的是一个PeopleResponseList(即多个PeopleResponse)
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
    public com.ctrip.flight.grpc3.MyResponse getRealNameByUsername(com.ctrip.flight.grpc3.MyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRealNameByUsernameMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.ctrip.flight.grpc3.PeopleResponse> getPeoplesByAge(
        com.ctrip.flight.grpc3.PeopleRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetPeoplesByAgeMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *第三种情况：向服务器端发送一个PeopleRequest，返回的是一个PeopleResponseList(即多个PeopleResponse)
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
    public com.google.common.util.concurrent.ListenableFuture<com.ctrip.flight.grpc3.MyResponse> getRealNameByUsername(
        com.ctrip.flight.grpc3.MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRealNameByUsernameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_REAL_NAME_BY_USERNAME = 0;
  private static final int METHODID_GET_PEOPLES_BY_AGE = 1;
  private static final int METHODID_GET_PEOPLES_WRAPPER_BY_AGES = 2;

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
          serviceImpl.getRealNameByUsername((com.ctrip.flight.grpc3.MyRequest) request,
              (io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.MyResponse>) responseObserver);
          break;
        case METHODID_GET_PEOPLES_BY_AGE:
          serviceImpl.getPeoplesByAge((com.ctrip.flight.grpc3.PeopleRequest) request,
              (io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponse>) responseObserver);
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
        case METHODID_GET_PEOPLES_WRAPPER_BY_AGES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getPeoplesWrapperByAges(
              (io.grpc.stub.StreamObserver<com.ctrip.flight.grpc3.PeopleResponseList>) responseObserver);
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
      return com.ctrip.flight.grpc3.People3Proto.getDescriptor();
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
              .addMethod(getGetPeoplesByAgeMethod())
              .addMethod(getGetPeoplesWrapperByAgesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
