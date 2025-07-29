package com.chat.handler;

import com.chat.message.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        System.out.println(String.format("LoginResponseHandler => Reciver Master Message: success=%s, reason=%s",msg.isSuccess(),msg.getReason()));
        System.out.println(msg.isSuccess() ? "Login success" : "Login failed: " + msg.getReason());
    }
}
