package com.netty.game.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyServerBootstrap implements CommandLineRunner {
    @Autowired
    private NettyInitializer nettyInitializer;

    private final int port = 8000;
    private EventLoopGroup boss = new NioEventLoopGroup(1);
    private EventLoopGroup worker = new NioEventLoopGroup();

    @Override
    public void run(String... args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(nettyInitializer);

        ChannelFuture future = bootstrap.bind(port).sync();
        log.info("Netty 服务已启动，端口：" + port);
        future.channel().closeFuture().sync();
    }

    @PreDestroy
    public void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}

