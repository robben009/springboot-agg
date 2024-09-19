package com.robben.agg.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class NettyClient {
    @Value("${server.bind_address}")
    private String host;
    @Value("${server.bind_port}")
    private Integer port;

    private boolean initFalg = true;

    // 创建事件循环组
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。 客户端的是Bootstrap，服务端的则是 ServerBootstrap。
     **/
    @PostConstruct
    public void init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        doConnect(bootstrap, group);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("正在停止客户端");
        try {
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        log.info("客户端已停止!");
    }

    /**
     * 重连
     */
    public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
        try {
            if (bootstrap != null) {
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);

                //如果连接在一段时间内没有数据传输，系统会自动发送保活探测包，以检查连接是否仍然有效
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.handler(new NettyClientInitializer());
                bootstrap.remoteAddress(host, port);

                channelFuture = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        log.info("与服务端断开连接!在10s之后准备尝试重连!");
                        eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
                    }
                });

                if (initFalg) {
                    log.info("Netty客户端启动成功!");
                    initFalg = false;
                }
            }
        } catch (Exception e) {
            log.info("客户端连接失败!" + e.getMessage());
        }

    }

}
