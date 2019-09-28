package com.ctrip.flight.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-20
 * Time: 12:00
 */
public class NewClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);// 在这设置成了阻塞形式，阻塞和非阻塞的区别在于：
                                             // 阻塞的话我们就可以一次性读取完，读取完了就返回；非阻塞的话需要多次不断的读取，在这我们采用一次性读取

        String file = "E:\\材料\\就业推荐表.doc";
        FileChannel fileChannel = new FileInputStream(file).getChannel();

        long startTime = System.currentTimeMillis();
        /** 底层源码解释：
         *  Many operating systems can transfer bytes directly from the source channel
         *  into the filesystem cache without actually copying them.
         *
         *  netty底层的零拷贝过程：
         *                           CPU copy
         *                     ________________________
         *                    |                        |
         *               kernel buffer           socket buffer
         *                    | DMA copy              |  DMA copy
         *               hard driver              protocol engine
         *
         *   真个过程是：首先hard driver 将磁盘数据（文件）拷贝到内核缓存kernel buffer中，接着内核缓存将文件描述符（包含文件起始位置和文件长度）
         *               传送给socket buffer，接着将文件描述符直接发送给协议引擎（protocol engine），然后协议引擎将直接去内核缓存中读取真正的文件数据。
         *               相当于在这我们省去了内核缓存到socket缓存之间数据的完全拷贝，只发送两个指针数据。
         */
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);// 在这使用的是零拷贝（transferTo()方法的底层实现）

        long endTime = System.currentTimeMillis();
        System.out.println("传输数据的字节数：" + transferCount + "耗时：" + (endTime - startTime));
        fileChannel.close();

    }
}
