// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HiService.proto

package com.yicj.study.grpc.model;

public final class HiService {
  private HiService() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yicj_study_grpc_model_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yicj_study_grpc_model_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yicj_study_grpc_model_HelloRequest_TagsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yicj_study_grpc_model_HelloRequest_TagsEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yicj_study_grpc_model_HelloResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yicj_study_grpc_model_HelloResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017HiService.proto\022\031com.yicj.study.grpc.m" +
      "odel\"\250\001\n\014HelloRequest\022\014\n\004name\030\001 \001(\t\022\013\n\003a" +
      "ge\030\002 \001(\005\022\017\n\007hobbies\030\003 \003(\t\022?\n\004tags\030\004 \003(\0132" +
      "1.com.yicj.study.grpc.model.HelloRequest" +
      ".TagsEntry\032+\n\tTagsEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005" +
      "value\030\002 \001(\t:\0028\001\"!\n\rHelloResponse\022\020\n\010gree" +
      "ting\030\001 \001(\t2j\n\014HelloService\022Z\n\005hello\022\'.co" +
      "m.yicj.study.grpc.model.HelloRequest\032(.c" +
      "om.yicj.study.grpc.model.HelloResponseB\002" +
      "P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_yicj_study_grpc_model_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_yicj_study_grpc_model_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yicj_study_grpc_model_HelloRequest_descriptor,
        new java.lang.String[] { "Name", "Age", "Hobbies", "Tags", });
    internal_static_com_yicj_study_grpc_model_HelloRequest_TagsEntry_descriptor =
      internal_static_com_yicj_study_grpc_model_HelloRequest_descriptor.getNestedTypes().get(0);
    internal_static_com_yicj_study_grpc_model_HelloRequest_TagsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yicj_study_grpc_model_HelloRequest_TagsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_com_yicj_study_grpc_model_HelloResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_yicj_study_grpc_model_HelloResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yicj_study_grpc_model_HelloResponse_descriptor,
        new java.lang.String[] { "Greeting", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}