package com.ctrip.flight.nio.bytebuf;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-03
 * Time: 15:19
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 *    Netty提供了三种缓冲类型：
 *    1. heap buffer;
 *    2. direct buffer;
 *    3. composite buffer。
 *
 *    Heap Buffer（堆缓冲区）： 这是最常用的类型，ByteBuf将数据存储到JVM的堆空间中，并且将真实的数据存储到byte array中实现。
 *    优点：由于数据存放在JVM堆中，因此可以快速的创建与快速的释放，并且它提供了直接访问内部字节数组的方法；
 *    缺点：每次读写数据时，都需要先将数据复制到直接缓冲区中再进行网络传输。
 *
 *    Direct Buffer（直接缓冲区）：在堆之外直接分配内存空间，直接缓冲区并不会占用堆的容量空间，因为它是由操作系统在native memory中进行的数据分配；
 *    优点：在使用Socket进行数据传输时，性能非常好，因为直接缓冲区是在native memory中进行分配的，性能很好；
 *    缺点：内存空间的释放要比堆空间要复杂，而且速度要慢一些。
 *
 *    为了解决这一缺点，Netty通过内存池来解决这个问题，即，将Direct Buffer在内存池中分配，每次缓冲区的创建无需新分配内存了，直接在内存池中进行。
 *    直接缓冲区并不支持通过字节数组的形式赖访问数据。
 *
 *    经验：在我们编写的后端业务消息的编解码（即我们编写的一个一个的handler），我们使用HeapByteBuf；对于I/O通信线程在读写缓冲区时，推荐使用DirectByteBuf
 *
 *
 *    JDK中的ByteBuffer和Netty中的ByteBuf的区别：
 *    1. Netty的ByteBuf采用读写分离的策略（readerIndex和writeIndex），一个初始化（里面尚未含有任何数据）的ByteBuf里面的readerIndex和writeIndex都为0；
 *    2. 当读索引和写索引位于同一个位置时，如果我们继续读取，就会抛出IndexOutOfBoundException；
 *    3. 对于ByteBuf的任何读写操作都会分别维护读索引和写索引。
 *
 *    JDK中ByteBuffer的缺点：
 *     1. final byte[] hb; 这是JDK的ByteBuffer中用于存储数据的对象声明，可以看到，其字节数组被声明为final的，也就是说长度是固定不变的，一旦
 *        分配好后不能动态扩容和缩容，而且当存储的字节数组很大时就很有可能出现IndexOutOfBoundException，如果要预防这个异常，那么就要在存储之前
 *        完全确定好待存储的字节大小。如果ByteBuffer的空间不足，我们只有一种解决方案：创建一个全新的ByteBuffer对象，然后再将之前的ByteBuffer中的
 *        数据复制过去，这一切操作都需要开发者自己手动完成。
 *     2. ByteBuffer只用一个position指针来标识位置信息，在进行读写切换时就需要调用flip()方法或者rewind()方法，使用起来很不方便。
 *
 *     Netty中ByteBuf的优点：
 *     1. 存储字节的数组是动态的，它的最大值是Integer.MAX_VALUE，这里的动态性体现在write()方法中，write()方法在执行时会判断buffer容量，如果不足则自动扩容；
 *     2. ByteBuf的读写完全分离，使用起来很方便。
 */
public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.buffer(8);

        // 通过可变数组添加ByteBuf
        compositeByteBuf.addComponents(heapBuf, directBuf);

        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
     // JDK 8来实现迭代
//      compositeByteBuf.forEach(System.out::println);
    }
}
