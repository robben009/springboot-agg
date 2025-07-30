package com.chat.common;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ChannelManager {

    private static final ConcurrentMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();

    public static void register(String userId, Channel channel) {
        userChannelMap.put(userId, channel);
    }

    public static void unregister(Channel channel) {
        String userId = channel.attr(ChannelAttrKeys.USER_ID).get();
        if (userId != null) {
            userChannelMap.remove(userId);
            log.info("用户下线：" + userId);
        }
    }

    public static Channel getChannel(String userId) {
        return userChannelMap.get(userId);
    }

    public static boolean isOnline(String userId) {
        return userChannelMap.containsKey(userId);
    }

}
