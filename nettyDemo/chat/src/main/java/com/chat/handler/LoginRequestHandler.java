package com.chat.handler;

import com.chat.SessionUtil;
import com.chat.message.LoginRequestPacket;
import com.chat.message.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        System.out.println(String.format("LoginRequestHandler => Client request Login,Info: username=%s, password=%s",
                msg.getUsername(), msg.getPassword()));

        SessionUtil.bindSession(ctx.channel());
        LoginResponsePacket responsePacket = new LoginResponsePacket(true, "Login success");
        ctx.channel().writeAndFlush(responsePacket);
    }
}
