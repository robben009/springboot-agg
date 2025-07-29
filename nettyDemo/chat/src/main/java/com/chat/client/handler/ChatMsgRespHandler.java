package com.chat.client.handler;

import com.chat.message.ChatMsgPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatMsgRespHandler extends SimpleChannelInboundHandler<ChatMsgPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsgPacket msg) {
        log.info(msg.getFromUser() + ": " + msg.getMessage());
    }

}
