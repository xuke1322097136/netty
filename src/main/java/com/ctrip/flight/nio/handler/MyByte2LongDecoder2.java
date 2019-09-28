package com.ctrip.flight.nio.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-07
 * Time: 22:38
 * ReplayingDecoder支持的泛型是Enum和Void（Void在这表示的是不需要使用状态管理的话，就用Void这个类）
 * 但是也需要看看ReplayingDecoder源码里的Limitations
 * 解释一下Limitations里面的例子：
 *    public class MyDecoder extends ReplayingDecoder<Void> {
 *
 *    private final Queue<Integer> values = new LinkedList<Integer>();
 *
 *     @Override
 *    public void decode(.., ByteBuf buf, List<Object> out) throws Exception {
 *
 *      // A message contains 2 integers.
 *      values.offer(buf.readInt());
 *      values.offer(buf.readInt());
 *
 *      // This assertion will fail intermittently since values.offer()
 *      // can be called more than two times!
 *      assert values.size() == 2;
 *      out.add(values.poll() + values.poll());
 *    }
 *  }
 *
 *    第一次进入decode方法里面，buf里面只有一个int，第一行的values.offer(buf.readInt());会执行，而第二行的values.offer(buf.readInt());
 *    再执行的时候就会抛出异常了，因为没有int了，流程将再次返回到MyDecoder中，接下来过了一段时间Bytebuf又来了一个元素，它又尝试着调用decode
 *    方法，然后接着读取一个int，然后接着第二行的offer又能读取到一个int，此时values里面有3个元素了。所以断言不是2
 */


public class MyByte2LongDecoder2 extends ReplayingDecoder<Void> {

    // 在这无需再对输入的ByteBuf当中的字节数够不够我们读取，其实不够的话，也不会使得程序崩掉。但是直接继承ByteToMessageDecoder是需要判断的
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ByteToMessageDecoder2 decode method invoked!");

        out.add(in.readLong());
    }
}
