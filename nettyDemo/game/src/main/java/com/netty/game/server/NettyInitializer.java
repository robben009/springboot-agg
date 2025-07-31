package com.netty.game.server;

import com.netty.game.model.GameMsgDto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class NettyInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ApplicationContext context;


    @Override
    protected void initChannel(SocketChannel ch) {
        //基于 Protobuf varint32 变长编码的消息长度，实现拆分 TCP 粘包/半包现象。它根据 protobuf 的长度字段，将字节流切分成一条条完整的 protobuf 消息。
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        // 解码器
        ch.pipeline().addLast(new ProtobufDecoder(GameMsgDto.GameMsg.getDefaultInstance()));

        /**
         * 在发送端给每个消息前面加上 protobuf 的 varint32 格式的长度字段,这样，接收端可以知道每条消息的长度，正确拆包,
         * 它是编码端（发送端）的长度字段写入器，和 ProtobufVarint32FrameDecoder 配合使用
         */
        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender()); // 加长度
        // 编码器
        ch.pipeline().addLast(new ProtobufEncoder());

        ch.pipeline().addLast(context.getBean(NettyServiceHandler.class)); // 业务逻辑
    }

}
