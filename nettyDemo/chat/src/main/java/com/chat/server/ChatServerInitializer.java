package com.chat.server;

import com.chat.codec.PacketDecoder;
import com.chat.codec.PacketEncoder;
import com.chat.handler.ChatMessageHandler;
import com.chat.handler.ExceptionHandler;
import com.chat.handler.LoginRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());

        pipeline.addLast(new LoginRequestHandler());
        pipeline.addLast(new ChatMessageHandler());
        pipeline.addLast(new ExceptionHandler());
    }
}
