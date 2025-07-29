package com.chat.client;

import com.chat.codec.PacketDecoder;
import com.chat.codec.PacketEncoder;
import com.chat.handler.ChatMessageResponseHandler;
import com.chat.handler.ExceptionHandler;
import com.chat.handler.LoginResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());

        pipeline.addLast(new LoginResponseHandler());
        pipeline.addLast(new ChatMessageResponseHandler());
        pipeline.addLast(new ExceptionHandler());
    }
}
