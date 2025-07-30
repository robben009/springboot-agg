package com.chat.server.handler;

import com.chat.common.SessionUtil;
import com.chat.message.ChatMsgPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatMsgHandler extends SimpleChannelInboundHandler<ChatMsgPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsgPacket msg) {
        for (Channel channel : SessionUtil.getAllChannels()) {
            if (channel != ctx.channel()) {
                channel.writeAndFlush(msg);
            }
        }
    }
}
