package com.chat.client;

import com.chat.client.handler.ChatMsgRespHandler;
import com.chat.client.handler.LoginRespHandler;
import com.chat.client.handler.StatusRespHandler;
import com.chat.codec.PacketDecoder;
import com.chat.codec.PacketEncoder;
import com.chat.handler.ExceptionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        //下面的第一个参数"decoder"可以省略,甚至顺序也可以打乱(但不建议这样做)
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());

        pipeline.addLast(new LoginRespHandler());
        pipeline.addLast(new ChatMsgRespHandler());
        pipeline.addLast(new StatusRespHandler());
        pipeline.addLast(new ExceptionHandler());
    }
}
