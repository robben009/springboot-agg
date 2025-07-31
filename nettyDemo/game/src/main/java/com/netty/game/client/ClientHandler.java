package com.netty.game.client;

import com.netty.game.model.GameMsgDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<GameMsgDto.GameMsg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 连接成功后立即发送消息
        GameMsgDto.GameMsg.Builder req = GameMsgDto.GameMsg.newBuilder();
        req.setMsg("line");
        ctx.writeAndFlush(req.build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameMsgDto.GameMsg msg) {
        log.info("服务端回复消息={}", msg.getMsg());
    }

}
