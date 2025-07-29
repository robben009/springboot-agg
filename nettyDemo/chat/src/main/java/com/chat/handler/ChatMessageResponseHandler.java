package com.chat.handler;

import com.chat.message.ChatMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatMessageResponseHandler extends SimpleChannelInboundHandler<ChatMessagePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessagePacket msg) {
        System.out.println(msg.getFromUser() + ": " + msg.getMessage());
    }
}
