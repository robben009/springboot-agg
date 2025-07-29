package com.chat.handler;

import com.chat.SessionUtil;
import com.chat.message.ChatMessagePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessagePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessagePacket msg) {
        msg.setFromUser(ctx.channel().remoteAddress().toString());
        for (Channel channel : SessionUtil.getAllChannels()) {
            if (channel != ctx.channel()) {
                channel.writeAndFlush(msg);
            }
        }
    }
}
