package com.ctrip.flight.nio.nio_test;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-14
 * Time: 15:29
 */

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering和Gathering的知识：
 * Scattering: 表示的是分散成多个，关于Buffer的读的时候，我们不仅何以传递单个Buffer还能传递一个Buffer数组，将来自于一个Channel的数据读到多个Buffer中去，
 *             读取的规则是按照顺序来读取的，并且是先读满了第一个Buffer才会对第二个Buffer进行读取，第一个没有读满第二个Buffer是不会读的；
 * Gathering : 和Scattering 正好是相反的，即往Channel中写数据的时候，只有第一个Buffer中所有的数据都写到了Channel中才会写第二个Buffer中的数据。
 *
 * 应用场景：请求数据的第一个Header为10个字节，第二个Header为5个字节，然后Body为2个字节，这样发送过来的时候我们就能一次读取到Buffer中去，
 *           无需我们自己手动进行分离了。
 */
public class NIOTest7 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocket.bind(address);


        int messageLength = 9;// (9 = 2 + 3 +4),在测试的时候，我们用telnet localhost 8899进行连接，注意回车键也是一个字符，我们可以用<9,=9,>9三种情况进行测试
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChanne1 = serverSocketChannel.accept();// 阻塞方法

        while (true){
            long byteRead = 0;
            long byteWrite = 0;
            while (byteRead < messageLength){
                long read = socketChanne1.read(buffers);// 在这读取的是整个Buffer数组
                byteRead += read;

                System.out.println("已读字节大小byteRead:" + byteRead);

                //往Buffer里面写完之后，我们观察一下每个buffer的position和limit的位置
                Arrays.asList(buffers).stream().
                        map(buffer -> "position:" + buffer.position() + ", limit:" + buffer.limit()).
                        forEach(System.out::println);
            }
             // 在准备往Channel中往回写的时候，我们需要将每个Buffer反转一下
            Arrays.asList(buffers).
                    forEach(buffer -> {buffer.flip();
                    });

            // 往回写：Gathering
              while (byteWrite < messageLength){
                  long write = socketChanne1.write(buffers);
                  byteWrite += write;
              }
              // 需要将每个buffer重置为初始值
            Arrays.asList(buffers).
                    forEach(buffer -> buffer.clear());

        }


    }

}
