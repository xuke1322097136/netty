package com.ctrip.flight.nio.nio_test;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-14
 * Time: 17:07
 */

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 传统的网络编程的框架/模式/风格(伪代码)：
 * 服务器端：ServerSocket  serverSocket = ServerSocket.open();
 *           serverSocket.blind(8899);// 绑定一个连接端口
 *            while(true){
 *                Socket socket = serverSocket.accept();// 阻塞，等待客户端连接进而通信
 *
 *                new Thread(socket);// 正对每一个客户端都会创建一个新的线程用于连接客户端
 *
 *                // 执行真正的数据传输操作
 *               run(){
 *                    socket.getInputStream();
 *                    ...
 *                    ...
 *                }
 *              }
 * 客户端： Socket socket = new Socket("localhost", 8899);
 *           socket.connect();
 *
 *    参考博客：https://www.jianshu.com/p/ab884ea62b43
 *              https://blog.csdn.net/an_tao/article/details/45914841（比较形象的例子）
 *              一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，
 *              然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。
 */
public class NIOTest8 {
    public static void main(String[] args) throws  Exception{
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();// SelectorProvider.provider().openSelector()方法打开的选择器

        for (int i = 0; i < ports.length; i++){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);//使通道为非阻塞
            ServerSocket serverSocket = serverSocketChannel.socket();// 获得服务器端socket对象
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);// 绑定到服务器端的一个端口号，表示服务器要监听哪一个端口号，客户端就会向该端口上进行连接

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// 将服务器端的5个Channel都注册到Selector上面，一开始是没有客户端连接的，
                                                                           // 所以 SelectionKey.OP_ACCEPT表示服务器端监听连接这么一个事件。

            System.out.println("客户端监听的端口号为： " + ports[i]);
        }

        while (true){
            int numbers = selector.select();// 调用了select()这个阻塞方法，它会阻塞到它所关注的SelectionKey中有事件发生的时候，它就会返回一个它所关注的事件的数量，
                                           // 然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。
            System.out.println("numbers: " + numbers);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();// 获取到该selector关联到的所有的Channel所对应的selectorKey
            System.out.println("SelectedKeys: " + selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                // 通过SelectionKey先得到ServerSocketChannel，然后负责阻塞监听，直到客户端输入。
                SelectionKey selectionKey = iterator.next();
                // 判断是否监听到客户端连接事件发生了（个人觉得像是初始化的意思，因为这个步骤只在第一次建立连接发生）。isAcceptable()与SelectionKey.OP_ACCEPT是相对应的。
                if (selectionKey.isAcceptable()){
                    // 之所以能强制类型转化为ServerSocketChannel，是因为上面我们注册的就是serverSocketChannel。
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();// 真正和客户端通信的套接字，其实这里socketChannel用client变量比较合适
                    socketChannel.configureBlocking(false);

                    // serverSocketChannel的任务是建立连接，它关注的是SelectionKey.OP_ACCEPT（连接这个事件）。
                    // 连接好之后，我们重新将客户端通道注册到Selector上去。socketChannel关注的是SelectionKey.OP_READ（数据读取这个事件）。
                    socketChannel.register(selector, SelectionKey.OP_READ);// 将SocketChannel也注册到该Channel上面，此时连接已经完成了

                    iterator.remove();// 将该key从Selected-Keys集合中删除掉，不然它还会监听这个事件，实际上这个事件我们已经建立完成了，所以需要删掉

                    System.out.println("获得客户端连接 " + socketChannel);
                }
                // 判断是否有数据过来了
                else if (selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    int bytesRead = 0;
                    while (true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();// 注意在这要clear，让position和limit两个指针变化
                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0){
                            break;
                        }

                        byteBuffer.flip();
                        // 往Buffer里面输入我们叫做读（调用read()方法），从Buffer里面输出我们叫写（调用write()方法）
                        socketChannel.write(byteBuffer);//操作系统会负责将buffer里面的东西刷新到磁盘上

                        bytesRead += read;
                    }
                    System.out.println("读取：" + bytesRead + "来自于：" + socketChannel);
                    iterator.remove();// 记住：每一个selectionKey处理完之后，一定要remove掉这个selectionKey。上面的remove方法同理
                }

            }

        }

    }
}
