package com.ctrip.flight.nio.nio_test;

import java.nio.ByteBuffer;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-13
 * Time: 16:28
 */

/**
 * 只读buffer和普通buffer
 * 我们可以随时将普通buffer调用asReadOnlyBuffer()方法变成一个只读buffer，而只读buffer不能转化为读写buffer
 */
public class NIOTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass()); //  class java.nio.HeapByteBuffer

        for (int i = 0; i < byteBuffer.capacity(); ++i){
            byteBuffer.put((byte)i);
        }

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());// class java.nio.HeapByteBufferR(多出来的R代表的是只读)


        readOnlyBuffer.position(0);
        readOnlyBuffer.put((byte)1);  // 发现会报错，因为它是只读的buffer

    }
}
