package com.ctrip.flight.protobuf;

/**
 * Created by xuke
 * Description: 如何构造.proto文件，并利用protoc编译器自动生成代码。
 * Date: 2019-09-07
 * Time: 21:37
 *   介绍一下RPC库的使用原理：
 *   1）在JAVA中比较成熟，EJB中大量使用的技术：RMI(Remote Method Invoke),它最大的缺陷是只适用于Java代码，即客户端和服务器端都必须是Java
 *       在所有的RPC框架中，都存在着 代码生成 这么一个概念，即是说针对定义好的一些规范和标准自动去生成代码，在自动生成的代码中，
 *       完成序列化（编码）、反序列化（解码）以及网络传输的过程。自动生成的代码在客户端叫做stub(桩)，服务器端叫做skeleton(骨架)。
 *       当我们把客户端代码写好之后，当我们去调用的时候实际上是将调用的信息传递给stub，然后stub和skeleton底层通过socket通信，
 *       skeleton在接受到数据之后，会反序列化成服务器端真正能理解的方法调用，完成服务器端的调用。再调用完之后，skeleton会将
 *       结果传递给stub，stub再将结果反序列化为服务器端能理解的概念。
 *        client: stub
 *        server: skeleton
 *    2） RPC（Remote Procedure Call）远程过程调用，可以实现跨语言的调用，很多RPC框架都是跨语言的。
 *        基本上所有的RPC框架的编写模式都是类似的：
 *        a.) 定义一个接口说明文件（也叫idl文件，interface description language），它是一个文本文件，是独立于语言的，
 *            它描述了对象/结构体（thrift）/消息（protobuf）、对象成员、接口方法等一系列信息；
 *        b.) 通过RPC框架所提供的编译器，将接口说明文件翻译成具体语言文件(其实就是为了连接客户端和服务器端)；
 *        c.) 在客户端和服务器端分别引入RPC编译器所生成的文件，即可像调用本地方法一样调用远程方法。
 *   至于底层的数据传递都是由RPC框架所自动生成的问价来帮我们进行处理。传统的http调用或者是web service调用可以看作是广义上
 *   的RPC调用，可以将web service视为RPC的一种实现方法，但是web service相比于RPC来说，效率或者说性能要低一些，因为它的编解码速率较慢。
 *   而且传输方法也不相同，web service一般来说是基于http来传输的，而RPC一般都是基于socket来进行传输的，传输的效率肯定是socket更高一些。
 *   所以一般来说，对于一些比较关键的应用一般都采用RPC这种调用；在分布式应用中，公司的内网中的服务与服务的调用之间一般也是用的RPC来调用。
 *
 *
 * 本例子所使用的工具：protoc
 * 编译器工具protoc下载：https://github.com/protocolbuffers/protobuf/releases，并将该工具的bin目录添加到环境变量path中
 *                     （下载的是protoc-3.9.1-win64.zip而不是protobuf-java-3.9.1.zip）
 *
 *  Student.proto的每一行的意思可以参见：https://developers.google.cn/protocol-buffers/docs/javatutorial
 *  接下来执行命令：protoc --java_out=src/main/java  src/protobuf/Student.proto
 *                (第一个参数是生成的java代码放到哪里，第二个是源文件（即proto文件）位于哪里)
 */
public class ProtoBufTest {
    public static void main(String[] args) throws Exception{
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("zhangsan").
                setAge(20).
                setAddress("shanghai").
                build();

        // 在这转换成字节数组之后，我们就可以利用netty这种载体来完成两台机器之间（客户端/服务器端）的通信和传输。
        // 相当于编码（序列化：将对象类型转换成字节数组）的过程
        byte[] student2ByteArray = student.toByteArray();
        // 解码的过程（反序列化的过程：将字节数组转换为对象类型）
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student1);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}
