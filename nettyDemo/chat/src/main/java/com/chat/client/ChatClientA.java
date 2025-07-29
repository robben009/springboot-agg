package com.chat.client;

import com.chat.message.ChatMsgPacket;
import com.chat.message.LoginReqPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class ChatClientA {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());
            Channel channel = bootstrap.connect("localhost", 8000).sync().channel();

            // 登录消息
            LoginReqPacket loginPacket = new LoginReqPacket();
            loginPacket.setUsername("clientA");
            loginPacket.setPassword("123456");
            channel.writeAndFlush(loginPacket);

            // 发送消息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                channel.writeAndFlush(new ChatMsgPacket("clientA", line));
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
