package com.ctrip.flight.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * Created by xuke
 * Description:
 * Date: 2019-07-20
 * Time: 11:43
 */
public class NewServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel  = ServerSocketChannel.open();
        // serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(false);
        serverSocket.bind(new InetSocketAddress(8899));

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            int readCount = 0;
            while (readCount != -1){
                try {
                   readCount = socketChannel.read(byteBuffer);
                    // readCount += read;
                }catch (Exception e){
                    e.printStackTrace();
                }
                byteBuffer.rewind(); //rewind()在读写模式下都可用，它单纯的将当前位置置0，同时取消mark标记，仅此而已。
                                    // 也就是说写模式下limit仍保持与Buffer容量相同，只是重头写而已
            }
        }
    }
}
