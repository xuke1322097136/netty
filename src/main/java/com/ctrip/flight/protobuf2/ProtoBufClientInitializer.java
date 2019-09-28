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
 * Date: 2019-07-21
 * Time: 18:22
 */
public class ProtoBufClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();

        // 接下来往pipe里面添加pipeline，可以省去名字（即第一个参数）
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufDecoder(MultiDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufEncoder());

        // 其实也可以用匿名内部类来实现
       pipeline.addLast(new ProtoBufClientHandler());
    }
}
