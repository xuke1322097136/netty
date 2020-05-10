package com.ctrip.flight.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-16
 * Time: 22:11
 * https://www.dazhuanlan.com/2020/01/30/5e31fadeaf23a/
 */
public class NioServer {
    private static Map<String, SocketChannel> clientMap = new HashMap<>();// 客户端之间通信，所以需要保存哪一个客户端发送的消息（key），然后发送了啥(SocketChannel)
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket =  serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocket.bind(address);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// 服务器端监听连接事件

        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);// 将客户端的channel也注册到这个Selector上

                            String key = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(key, client);
                        }else if (selectionKey.isReadable()){
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                            int count = client.read(readBuffer);
                            if (count > 0){
                                readBuffer.flip(); // 牵涉到读-写，所以在这需要翻转一下
                                Charset charset = Charset.forName("utf-8");
                                // 把字节码数组转化为真正的字符串对象
                                // 解码：将磁盘上的文件（涉及到存储都是以字节形式存在的）转换成字符或者是字符串相关的内容
                                // 编码：将一个字符或者是字符串转换成字节或者是字节数组相关的内容
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());

                                System.out.println(client + ":" + receiveMessage);

                                // 服务器端接受到消息之后，要转发给各个已连接的客户端，而且要拿到key，因为要告知其余客户端是谁发送过来的
                                // 而我们可以通过map里面的value（SocketChannel，即client）反推出key，首先我们找到是哪个客户端发的，找到他的key
                                String senderKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
                                    if (entry.getValue() == client){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                 // 将消息转发给每一个已连接的SocketChannel对象
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel value = entry.getValue();

                                    // 把数据放置到Buffer里面叫做读，把数据从Buffer取出来叫作写
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((senderKey + ":" + receiveMessage).getBytes());

                                    writeBuffer.flip();

                                    value.write(writeBuffer);
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();// 在这一定要记得将使用过的key删除掉，和之前的每一个key调用remove()方法是一个效果。
            }catch (Exception e){
                e.printStackTrace();
            }
            }
    }
}
