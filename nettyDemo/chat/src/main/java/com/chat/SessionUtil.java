package com.chat;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<Channel, String> channels = new ConcurrentHashMap<>();

    public static void bindSession(Channel channel, String username) {
        channels.put(channel, username);
    }

    public static String getBindData(Channel channel) {
        return channels.get(channel);
    }


    public static void unbindSession(Channel channel) {
        channels.remove(channel);
    }

    public static Set<Channel> getAllChannels() {
        return channels.keySet();
    }
}
