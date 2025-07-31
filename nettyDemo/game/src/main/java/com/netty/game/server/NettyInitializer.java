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
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder()); // 处理半包
        ch.pipeline().addLast(new ProtobufDecoder(GameMsgDto.GameMsg.getDefaultInstance())); // 解码器

        ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender()); // 加长度
        ch.pipeline().addLast(new ProtobufEncoder()); // 编码器

        ch.pipeline().addLast(context.getBean(NettyServiceHandler.class)); // 业务逻辑
    }

}
