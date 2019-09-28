package com.ctrip.flight.nio.nio_test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-13
 * Time: 16:54
 */
public class NIOTest6 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("NioTest6_input.txt");
        FileOutputStream outputStream = new FileOutputStream("NioTest6_output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        /**
         * 利用HeapByteBuffer进行IO操作的时候，会在堆中先生成一个字节数组，然后会有一个数据拷贝的过程，即在堆外内存（也叫native内存，位于操作系统的某一块内存中）
         * 中拷贝一份字节数组，然后这个数据会跟IO设备直接进行交互；
         * 而DirectByteBuffer完成IO时则只是在堆中生成了一个DirectByteBuffer对象，真正的数据不在堆中，而在OS内存中。
         * 它完成IO的时候无需拷贝，不会在堆内存中生成字节数组，直接在堆外生成一份字节数组，Java内存布局中使用address这个变量来访问该字节数组，而该字节数组将直接提供IO操作。
         *
         * 为什么需要在OS中（堆外）拷贝一份字节数组？
         * 不是因为native的方法（即OS系统中的内存）不能直接访问JVM堆中的字节数组，在内核状态下，OS可以操作任何一块内存的数据，而是为了避免我们在操作
         * 堆中字节数组的时候发生垃圾回收GC，GC的时候是需要发生数据移动的，即将留下来的内存重新组合起来组成相对更大的内存。而我们如果不拷贝一份的话，
         * 那访问的数据将会乱套了。在拷贝（从JVM堆 -> OS内存区域）的时候，JVM会保证不会发生GC
         */
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);
        while (true){
            byteBuffer.clear(); //注意有没有clear()方法的区别，没有的话将无限循环，一直输出文本中的内容，因为每当position渡到limit位置的时候，
                               //  调用clear之后，它又会回到0的位置了。
            int read = inputChannel.read(byteBuffer);
            System.out.println("read: " + read);

            if(read <= 0){
                break;
            }

             byteBuffer.flip();
            outputChannel.write(byteBuffer);// 读取到ByteBuffer之后，操作系统会将Buffer里面的数据刷新到磁盘文件中去
        }

        inputChannel.close();
        outputChannel.close();

    }
}
