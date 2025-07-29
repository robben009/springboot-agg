package com.chat.client.handler;

import com.chat.message.StatusMsgPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatusRespHandler extends SimpleChannelInboundHandler<StatusMsgPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StatusMsgPacket statusMsgPacket) {
       log.info(statusMsgPacket.getLoginMsg());
    }
}
