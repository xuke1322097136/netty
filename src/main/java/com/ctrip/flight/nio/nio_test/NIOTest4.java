package com.ctrip.flight.nio.nio_test;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-13
 * Time: 16:06
 */

import java.nio.ByteBuffer;

/**
 * Slice Buffer可以理解为原有buffer的一个快照，它们共享同一块内存，共享同一个数组，一个buffer的数据发生变化，则两个buffer的数据都能得到体现
 * 下面的例子可以看到第2到第5个位置（左含position右不含limit）的数据都发生了变化，变成了原来的2倍，其他的都不变。
 */

/**
 *   ByteBuffer里面常见的方法：
 *   1. flip()方法：1）将limit设置为当前的position值；2) position设置为0；
 *   2. clear()方法： 1）将limit设置为capacity值；2)position设置为0；
 *   3. compact()方法： 1）将所有未读取到的数据复制到buffer起始位置；2）将position设置为最后一个未读元素的后面；3）将limit设置为capacity；
 *                       4） 现在buffer就准备好了，但不会覆盖未读的元素。
 */
public class NIOTest4 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); ++i){
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(6);

        ByteBuffer sliceBuffer = byteBuffer.slice();//切片

        for(int i = 0 ; i < sliceBuffer.capacity(); ++i){
            byte b = sliceBuffer.get();
            b *= 2;
            sliceBuffer.put(i, b);
        }

//        重新指定索引位置，byteBuffer.rewind()可以达到同样的效果
        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());

        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }




    }
}
