package com.ctrip.flight.protobuf2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by xuke
 * Description:
 * Date: 2019-09-08
 * Time: 23:15
 * MyServerInitializer的作用是往pipeline中添加一个一个的handler，最后添加完了之后，它会在程序运行的时候（addLast方法）中把自己删除掉
 */
public class ProtoBufServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // ProtobufDecoder里面传递的是一个MessageLite，因此我们需要传入一个实例对象（ProtobufDecoder的注释写的）
        pipeline.addLast(new ProtobufDecoder(MultiDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast(new ProtoBufServerHandler());
    }
}
