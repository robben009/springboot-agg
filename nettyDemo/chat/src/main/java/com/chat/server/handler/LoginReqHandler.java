package com.chat.server.handler;

import com.chat.common.ChannelAttrKeys;
import com.chat.common.ChannelManager;
import com.chat.common.SessionUtil;
import com.chat.message.LoginReqPacket;
import com.chat.message.LoginRespPacket;
import com.chat.message.StatusMsgPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginReqHandler extends SimpleChannelInboundHandler<LoginReqPacket> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ChannelManager.unregister(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();

        // 获取离线用户名字
        String username = SessionUtil.getBindData(channel);

        // 清除 session 绑定
        SessionUtil.unbindSession(channel);

        // 广播离线通知给所有在线用户
        for (Channel ch : SessionUtil.getAllChannels()) {
            if (ch != channel) {
                StatusMsgPacket statusMsgPacket = new StatusMsgPacket(username + "_离开了聊天室");
                ch.writeAndFlush(statusMsgPacket);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginReqPacket msg) {
        ByteBuf buf = Unpooled.buffer(128);

        log.info(String.format("LoginRequestHandler => Client request Login,Info: username=%s, password=%s",
                msg.getUsername(), msg.getPassword()));

        //绑定用户channel
        SessionUtil.bindSession(ctx.channel(), msg.getUsername());
        //还可以通过channel.attr来绑定用户信息(先设置属性,再去注册到连接池中)  两个选一个就行了,一般中下面这个
        ctx.channel().attr(ChannelAttrKeys.USER_ID).set(msg.getUsername());
        ChannelManager.register(msg.getUsername(), ctx.channel());

        //回复登录成功包
        LoginRespPacket responsePacket = new LoginRespPacket(true, "Login success");
        ctx.channel().writeAndFlush(responsePacket);

        //通知其他用户有人登录
        for (Channel channel : SessionUtil.getAllChannels()) {
            if (channel != ctx.channel()) {
                StatusMsgPacket statusMsgPacket = new StatusMsgPacket(msg.getUsername() + "_加入聊天室");
                channel.writeAndFlush(statusMsgPacket);
            }
        }
    }




}
