package com.netty.game.server;

import com.netty.game.model.GameMsgDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NettyServiceHandler extends SimpleChannelInboundHandler<GameMsgDto.GameMsg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("接收到了客户端链接请求={}", System.currentTimeMillis());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameMsgDto.GameMsg gameMsg) {
        log.info("接收到了客户端请求={}", gameMsg.getMsg());

        // 回传响应（可以直接复用 Protobuf 对象）
        GameMsgDto.GameMsg.Builder resp = GameMsgDto.GameMsg.newBuilder();
        resp.setMsg(gameMsg.getMsg() + "回复了");

        //由此可以做扩展点处理一系列的请求

        ctx.writeAndFlush(resp.build());
    }

}
