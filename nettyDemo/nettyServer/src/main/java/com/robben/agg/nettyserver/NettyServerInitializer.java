package com.robben.agg.nettyserver;

import com.robben.agg.nettycommon.protoMsg.UserMsg;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;

import java.util.concurrent.TimeUnit;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();

        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        ph.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        // 解码和编码，应和客户端一致
        //传输的协议 Protobuf
        ph.addLast(new ProtobufVarint32FrameDecoder()); // 处理半包
        ph.addLast(new ProtobufDecoder(UserMsg.User.getDefaultInstance())); // 解码器
        ph.addLast(new ProtobufVarint32LengthFieldPrepender()); // 加长度
        ph.addLast(new ProtobufEncoder()); // 编码器

        //业务逻辑实现类
        ph.addLast("serverBizHandler", new ServerBizHandler());
    }
}
