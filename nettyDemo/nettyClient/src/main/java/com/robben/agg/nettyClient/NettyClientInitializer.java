package com.robben.agg.nettyClient;

import com.robben.agg.nettycommon.protoMsg.UserMsg;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {


    /**
     * 接收消息时的顺序 IdleStateHandler、ProtobufVarint32FrameDecoder、ProtobufDecoder、NettyClientHandler
     * 发送消息时的顺序 NettyClientHandler、ProtobufEncoder、ProtobufVarint32LengthFieldPrepender
     *
     * @param ch
     */
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline ph = ch.pipeline();
        /*
         * 解码和编码，应和服务端一致
         * */
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        ph.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));

        //传输的协议Protobuf
        ph.addLast(new ProtobufVarint32FrameDecoder());          //解码器，用于解码以 varint32 长度前缀的 Protobuf 消息。
        ph.addLast(new ProtobufDecoder(UserMsg.User.getDefaultInstance())); //将字节流解码为 Protobuf 的 UserMsg.User 对象
        ph.addLast(new ProtobufVarint32LengthFieldPrepender());        //在消息发送前添加 varint32 长度前缀。
        ph.addLast(new ProtobufEncoder());        //将 Protobuf 对象编码为字节流。

        //业务逻辑实现类
        ph.addLast("clientBizHandler", new ClientBizHandler());
    }

}
