package com.ctrip.flight.nio.nio_test;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-12
 * Time: 22:34
 *
 * Netty视频讲解第32讲（张龙）
 * 字节流：InputStream/OutputStream；
 * 字符流：Reader/Writer
 * 节点流：FileInputStream/FileOutputStream等
 * 过滤流：FilterInputStream等
 * 装饰模式
 */
/**
 * 快捷键：快速关闭ternimal：shift+esc
 *         IDEA点错了默认方法，如：buffer.position(),发现我们想点的是limit()方法，
 *         此时可以直接光标顶到buffer.这里，然后找到要的方法，直接tab键，后面的position方法将自动删除,省去了手动删除。
 */

/**
 * Selector;                          S
 *                            /       |      \
 * Channel:                  C        C      C
 *                          /         |       \
 * Buffer:                  B         B        B   <------> 我们的程序
 *
 * java.io中最为核心的一个概念是流（Stream），是面向流的编程。在Java中，一个流要么是输出流要么是输出流，不可能是输入流又是输出流。
 * java.nio中有3个核心的概念：Selector(选择器)、Channel(通道，可以类比于java.io中的Stream)、Buffer(缓存区)，在java.nio中，我们面向块（block）或者缓冲区（buffer）编程的，
 *         Buffer本身就是一块内存区域，每一个channel必须有与之对应的Buffer，在底层实现上，它其实是一个数组，数据的读、写都是通过Buffer来实现的。
 *
 * 除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程。
 *
 * Channel指的是可以向其写入数据或者从中读取数据的对象，它类比于java.io中的Stream。
 * 所有数据的读写都是通过Buffer来进行的，永远不会出现直接向Channel写入数据的情况，或者是直接从Channel中读取数据的情况。
 * 与Stream不同的是，Channel是双向的，一个流只可能是InputStream或者OutputStream，而Channel打开后是可以进行读操作、写操作和读写操作的。
 * 由于Channel是双向的，所以它能更好的反映出底层操作系统的真实情况，在Linux系统中，底层操作系统的通道就是双向的。
 *
 *
 * 测试程序1：通过NIO的方式，随机产生的10个随机数输出到控制台；
 * 测试程序2：通过NIO的方式，读取文件中的字符输出到控制台；
 * 测试程序3：通过NIO的方式，程序输入的字符输出到指定文件中。
 */

/**
 * Java NIO中，关于DirectBuffer，HeapBuffer的疑问？
 * 知乎上面的解答： https://www.zhihu.com/question/57374068/answer/152691891
 */
public class NIOTest1 {
    public static void main(String[] args) {
        IntBuffer intBuffer  =  IntBuffer.allocate(10);//Java中的8种原生数据类型都有各自对应的Buffer，如IntBuffer、DoubleBuffer、ByteBuffer等等。
        System.out.println("IntBuffer的capacity:" + intBuffer.capacity());

        //for (int i = 0; i < intBuffer.capacity(); i++){
        for (int i = 0; i < 5; i++){
             int random = new SecureRandom().nextInt(20);
             intBuffer.put(random);
        }
        System.out.println("在flip之前，limit的值：" + intBuffer.limit());
        //每次先往缓冲区中写数据之后，紧接着完成缓存区读操作的话，我们都得对缓冲区执行flip()方法，其目的是为了实现状态的翻转
        // flip方法里面维护了三个整数值：0 <= mark <= position <= limit <= capacity(mark=-1的时候表示mark标记失效)
        // mark用来和reset搭配使用，比如我读到第5个元素的时候，这时候执行reset操作，下一次我在读的时候则从mark标记的位置开始读
        //刚开始初始化的时候，position初始化为0，capacity和limit的值都指向最后一个元素所在位置的下一个位置，mark暂时不用，每次往IntBuffer里写的时候只动position的值，往右移动
        //在flip之后，limit指向position所指向的位置，即指向的是最后一个元素所在的索引的下一个索引，而position的值重新指向0.
        // clear方法完成的是将position、limit和capacity回到最初的状态，即position=0，limit = capacity，但是里面的元素其实是不会被清空的，仍然会保留下来
        intBuffer.flip();
        System.out.println("在flip之后，limit的值：" + intBuffer.limit());

        System.out.println("进入到IntBuffer");
        while (intBuffer.hasRemaining()){
            System.out.println("position的值：" + intBuffer.position());
            System.out.println("limit的值：" + intBuffer.limit());
            System.out.println("capacity的值" + intBuffer.capacity());


            System.out.println(intBuffer.get());
        }
    }
}
