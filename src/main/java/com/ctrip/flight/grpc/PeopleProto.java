// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: People.proto

package com.ctrip.flight.grpc;

public final class PeopleProto {
  private PeopleProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_ctrip_flight_grpc_MyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_ctrip_flight_grpc_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_ctrip_flight_grpc_MyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_ctrip_flight_grpc_MyResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014People.proto\022\025com.ctrip.flight.grpc\"\035\n" +
      "\tMyRequest\022\020\n\010username\030\001 \001(\t\"\036\n\nMyRespon" +
      "se\022\020\n\010realname\030\001 \001(\t2o\n\rPeopleService\022^\n" +
      "\025GetRealNameByUsername\022 .com.ctrip.fligh" +
      "t.grpc.MyRequest\032!.com.ctrip.flight.grpc" +
      ".MyResponse\"\000B&\n\025com.ctrip.flight.grpcB\013" +
      "PeopleProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_ctrip_flight_grpc_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_ctrip_flight_grpc_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_ctrip_flight_grpc_MyRequest_descriptor,
        new java.lang.String[] { "Username", });
    internal_static_com_ctrip_flight_grpc_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_ctrip_flight_grpc_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_ctrip_flight_grpc_MyResponse_descriptor,
        new java.lang.String[] { "Realname", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
