package com.robben.agg.nettyserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NettyServer {
    @Value("${server.bind_port}")
    private Integer port;
    @Value("${server.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${server.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${server.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${server.netty.max_payload_size}")
    private Integer maxPayloadSize;
    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    @PostConstruct
    public void init() throws Exception {
        log.info("Setting resource leak detector level to {}", leakDetectorLevel);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        log.info("开始启动服务端");
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyServerInitializer());

        // 绑定端口，并同步等待成功，即启动服务端
        channelFuture = serverBootstrap.bind(port).sync();
        log.info("服务端启动成功");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        log.info("Stopping Server");
        try {
            // 监听服务端关闭，并阻塞等待
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭两个 EventLoopGroup 对象
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        log.info("server stopped!");
    }

}




