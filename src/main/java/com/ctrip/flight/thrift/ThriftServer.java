package com.ctrip.flight.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;

/**
 * Created by xuke
 * Description: Thrift服务器端，跟protobuf不同的是，它自己就可以将编解码好的数据格式就进行传输，而protobuf则需要借助于netty等通信框架。
 * Date: 2019-09-09
 * Time: 0:48
 *    1.  Thrift的传输格式主要有：（协议层）
 *        a. TBinaryProtocol(二进制格式)；
 *        b. TCompactProtocol(压缩格式，这种格式是用的最多的)；
 *        c. TJSONProtocol(JSON格式)
 *        d. TSimpleJSONProtocol(提供JSON只写协议，生成的文件很容易通过脚本语言解析，很少使用)
 *        e. TDebugProtocol(使用移动的刻度的文本格式，以便于debug，可以使用抓包工具分析包传输过程)
 *     2. Thrift的数据传输方式主要有：（传输层）
 *        a. TSocket(阻塞式socket);
 *        b. TFramedTransport(以frame为单位进行传输，非阻塞式的服务中使用,使用的比较多)；
 *        c. TFileTransport(以文件形式进行传输)；
 *        d. TMemoryTransport(将内存用于I/O，Java实现时内部实际上使用了简单的ByteArrayOutputStream)；
 *        e. TZlibTransport(使用Zlib进行压缩，当前无Java实现)
 *      3. Thrift支持的服务模型主要有：
 *        a. TSimpleServer(简单的单线程服务模型，常用于测试)；
 *        b. TThreadPoolServer(多线程服务模型，使用标准的阻塞式IO)；
 *        c. TNonblockingServer(多线程服务模型，使用非阻塞式IO，需使用TFramedTransport数据传输方式)；
 *        d. THsHaServer(ThsHa引入了线程池去处理，模型将读写任务放到线程池中处理，Half-sync/Half-async的处理模式，Half-async是在
 *           处理IO事件上（accept/read/write io），Half-sync用于handler对rpc的同步处理，它也需要使用TFramedTransport数据传输方式，
 *           它使用的较多)
 *  注意：客户端和服务器端的传输格式/传输协议必须相同，不然无法解析消息，即对消息的序列化和反序列化才不会出错
 *
 */
public class ThriftServer {
    public static void main(String[] args) throws Exception{
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        THsHaServer server = new THsHaServer(arg);

        System.out.println("Thrift Server Started!");
        server.serve();// 这是一个异步非阻塞的方法，它是一个死循环

    }
}
