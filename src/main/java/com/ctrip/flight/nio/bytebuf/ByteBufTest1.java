package com.ctrip.flight.nio.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-03
 * Time: 15:19
 */
public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("我Hello world", Charset.forName("utf-8"));

        // 判断当前的ButeBuf背后的信息到底是堆上缓冲(数组是在堆上分配的)还是直接缓冲
        // 直接缓冲区并不是通过字节数组来存储的
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));

            // 输出结果为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 14, cap: 36)
            // 第一个ridx表示的是读索引，widx表示的是写索引（一个中文用Utf-8是3个字节），cap表示capacity容量。
            // 注意这里的capacity的值不是14，表明它跟JDK里面的ByteBuffer是不一样的，ByteBuf是支持动态扩容的，
            // 里面还有一个maxCapacity（Integer.MAX_VALUE）而ByteBuffer的capacity值是固定大小的。
            System.out.println(byteBuf);

            System.out.println("arrayOffset:" + byteBuf.arrayOffset());
            System.out.println("readerIndex:" + byteBuf.readerIndex());
            System.out.println("writerIndex:" + byteBuf.writerIndex());
            System.out.println("capacity:" + byteBuf.capacity());

            int length = byteBuf.readableBytes();
            System.out.println("readableBytes:" + length);

            for (int i = 0; i < byteBuf.readableBytes(); i++){
                // 由于中文的原因，中间会打印出乱码出来，因为一个中文是3个字节
                System.out.println((char)byteBuf.getByte(i));
            }
             // 我   占据了3个字节（第0-2个字节）
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));
        }
    }
}
