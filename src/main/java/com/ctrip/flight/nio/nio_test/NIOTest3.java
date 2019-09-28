package com.ctrip.flight.nio.nio_test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-12
 * Time: 23:24
 */
public class NIOTest3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        FileChannel fileChannel = fileOutputStream.getChannel();

        byte[] messages = "hello world, NioTest3".getBytes();
         byteBuffer.put(messages);

         byteBuffer.flip();

         while (byteBuffer.hasRemaining()){
             fileChannel.write(byteBuffer);
         }

         fileOutputStream.close();
    }
}
