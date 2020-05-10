package com.ctrip.flight.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-16
 * Time: 23:04
 *
 * Connect：连接完成事件( TCP 连接 )，仅适用于客户端，对应 SelectionKey.OP_CONNECT ；
 *
 * Accept：接受新连接事件，仅适用于服务端，对应 SelectionKey.OP_ACCEPT ；
 *
 * Read：读事件，适用于两端，对应 SelectionKey.OP_READ ，表示 Buffer 可读；
 *
 * Write：写时间，适用于两端，对应 SelectionKey.OP_WRITE ，表示 Buffer 可写。
 */
public class NioClient {
    public static void main(String[] args) {
         try {
             SocketChannel socketChannel = SocketChannel.open();
             socketChannel.configureBlocking(false);

             // socketChannel.socket().bind(new InetSocketAddress(8899));这是错误写法，客户端不是这么连接的
             Selector selector = Selector.open();
             socketChannel.register(selector, SelectionKey.OP_CONNECT);// 注意这里用的是客户端向服务器端发起连接事件，不是服务器端接受ACCEPT事件了。
             socketChannel.connect(new InetSocketAddress("localhost", 8899));

             while (true){
                 selector.select();
                 Set<SelectionKey> keySet =  selector.selectedKeys();
                 for (SelectionKey selectionKey : keySet){
                     // 返回true表示的是服务器端已经建立好了连接
                     if (selectionKey.isConnectable()){
                         SocketChannel client = (SocketChannel) selectionKey.channel();
                         // 如果连接操作是否处在进行状态，那么我们呢就完成连接
                         if (client.isConnectionPending()){
                             client.finishConnect();// 表示现在来连接已经真正的建立好了

                             // 通过一下已经连接好了（也可以不写，通过控制台输出方便测试）
                             ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                             writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                             writeBuffer.flip();
                             client.write(writeBuffer);

                             // 由于用户输入是一个阻塞操作，所以我们需要另起一个线程
                             ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                             // 可以是实现一个Runnable或者是Callable的线程，我们直接用lambda表达式来实现Runnable接口
                             executorService.submit(() ->{
                                 // 用户输入依然是一个死循环
                                 while (true){
                                     try {
                                         writeBuffer.clear();
                                         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// 过滤流包装节点流

                                         String sendMessage = br.readLine();//一行一行地读
                                          writeBuffer.put(sendMessage.getBytes());
                                          writeBuffer.flip();
                                          client.write(writeBuffer);
                                     }catch (Exception e){
                                         e.printStackTrace();
                                     }
                                 }
                             });

                         }
                         // 因为如果服务器端有数据过来的话，我们需要注册读取事件，用于读取服务器端数据的事件
                         client.register(selector, SelectionKey.OP_READ);
                     }
                     else if (selectionKey.isReadable()){
                         SocketChannel client = (SocketChannel) selectionKey.channel();

                         ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                         int count = client.read(readBuffer);

                         if (count > 0){
                             String receivedMessage = String.valueOf(readBuffer.array());
                             System.out.println(receivedMessage);
                         }
                     }
                 }
               keySet.clear();// 要清除掉已经处理掉的selectionKey
             }

         }catch (Exception e){
             e.printStackTrace();
         }


    }
}
