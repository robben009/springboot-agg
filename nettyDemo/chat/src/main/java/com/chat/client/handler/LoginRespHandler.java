package com.chat.client.handler;

import com.chat.message.LoginRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRespHandler extends SimpleChannelInboundHandler<LoginRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRespPacket msg) {
        log.info(String.format("LoginResponseHandler => Reciver Master Message: success=%s, reason=%s", msg.isSuccess(), msg.getReason()));
        log.info(msg.isSuccess() ? "Login success" + msg.getReason() : "Login failed: " + msg.getReason());
    }

}
