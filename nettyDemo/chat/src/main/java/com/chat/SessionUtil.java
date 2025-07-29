package com.chat;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<Channel, String> channels = new ConcurrentHashMap<>();

    public static void bindSession(Channel channel) {
        channels.put(channel, "login-in");
    }

    public static void unbindSession(Channel channel) {
        channels.remove(channel);
    }

    public static Set<Channel> getAllChannels() {
        return channels.keySet();
    }
}
