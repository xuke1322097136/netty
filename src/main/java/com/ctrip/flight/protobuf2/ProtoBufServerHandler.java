package com.ctrip.flight.protobuf2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by xuke
 * Description: 客户端可以传递不同的消息，服务器端可以看根据客户端端传输的消息类型来正确解析消息内容
 * Date: 2019-09-08
 * Time: 23:21
 */

public class ProtoBufServerHandler extends SimpleChannelInboundHandler<MultiDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MultiDataInfo.MyMessage msg) throws Exception {
        MultiDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MultiDataInfo.MyMessage.DataType.PersonType){
            MultiDataInfo.Person person = msg.getPerson();

            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());

        }else if (dataType == MultiDataInfo.MyMessage.DataType.DogType){
            MultiDataInfo.Dog dog = msg.getDog();

            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        }else{
            MultiDataInfo.Cat cat = msg.getCat();

            System.out.println(cat.getName());
            System.out.println(cat.getCity());
        }

    }

}
