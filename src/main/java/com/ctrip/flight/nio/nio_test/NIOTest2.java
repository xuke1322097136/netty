package com.ctrip.flight.nio.nio_test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-12
 * Time: 23:08
 */

/**
 * 使用NIO读取文件所涉及到的步骤：
 * 1. 从FileInputStream对象里面获取到Channel对象；
 * 2. 创建Buffer；
 * 3. 将数据从Channel中读取到ByteBuf中。
 */
public class NIOTest2 {
    public static void main(String[] args) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream("NioTest2.txt")) {
            FileChannel fileChannel = fileInputStream.getChannel();

            // 其实我们用到的ByteBuffer都是HeapByteBuffer（查看allocate方法的源码看到的）,
            //  只是该类不是一个public类型的类，所以只能在本包中使用，所以我们不能直接使用该类
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);//声明一个长度为512的字节数组
            fileChannel.read(byteBuffer);

            byteBuffer.flip();

            while (byteBuffer.remaining() > 0) {
                byte b = byteBuffer.get();
                System.out.println("Character:" + (char) b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
