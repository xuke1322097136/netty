package com.ctrip.flight.protobuf2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Created by xuke
 * Description:
 * Date: 2019-09-08
 * Time: 23:36
 */
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<MultiDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MultiDataInfo.MyMessage s) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);// 构造出0、1、2的随机数
        MultiDataInfo.MyMessage myMessage = null;
        if (0 == randomInt) {
            myMessage = MultiDataInfo.MyMessage.newBuilder().
                    setDataType(MultiDataInfo.MyMessage.DataType.PersonType).
                    setPerson(MultiDataInfo.Person.newBuilder().
                            setName("张三").setAge(20).
                            setAddress("beijing").build()).
                    build();
        } else if (1 == randomInt) {
            myMessage = MultiDataInfo.MyMessage.newBuilder().
                    setDataType(MultiDataInfo.MyMessage.DataType.DogType).
                    setDog(MultiDataInfo.Dog.newBuilder().
                            setName("一只狗").setAge(10).build()).
                    build();
        } else {
            myMessage = MultiDataInfo.MyMessage.newBuilder().
                    setDataType(MultiDataInfo.MyMessage.DataType.CatType).
                    setCat(MultiDataInfo.Cat.newBuilder().
                            setName("一只猫").setCity("shanghai").build()).
                    build();
        }
        ctx.channel().writeAndFlush(myMessage);
    }

}
