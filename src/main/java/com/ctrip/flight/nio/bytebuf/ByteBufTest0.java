package com.ctrip.flight.nio.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-03
 * Time: 14:39
 */

/**
 *  *      +-------------------+------------------+------------------+
 *  *      | discardable bytes |  readable bytes  |  writable bytes  |
 *  *      |                   |     (CONTENT)    |                  |
 *  *      +-------------------+------------------+------------------+
 *  *      |                   |                  |                  |
 *  *      0      <=      readerIndex   <=   writerIndex    <=    capacity
 */
public class ByteBufTest0 {
    public static void main(String[] args) {
        // 池化和非池化的区别在于：非池化的buffer是用完了就直接销毁掉
        ByteBuf byteBuf = Unpooled.buffer(5);

        for (int i = 0; i < 5; i++){
            byteBuf.writeByte(i);
        }

        // getByte方法是绝对方法，这种方法从ByteBuf里读出来的时候，并不会挪动readerIndex指针
        for (int i = 0; i < byteBuf.capacity(); i++){
            System.out.println("readerIndex:" + byteBuf.readerIndex() + "  writerIndex:" + byteBuf.writerIndex()+
                    "  data:" + byteBuf.getByte(i));
        }

        System.out.println(".............................");

        // readByte()为相对方法，readerIndex指针不断往右移动
        for (int i = 0; i < byteBuf.capacity(); i++){
            System.out.println("readerIndex:" + byteBuf.readerIndex() + "  writerIndex:" + byteBuf.writerIndex() +
                    "  data:" + byteBuf.readByte());
        }
    }
}
