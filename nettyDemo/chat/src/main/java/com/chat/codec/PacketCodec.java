package com.chat.codec;

import com.alibaba.fastjson2.JSON;
import com.chat.Command;
import com.chat.message.ChatMessagePacket;
import com.chat.message.LoginRequestPacket;
import com.chat.message.LoginResponsePacket;
import com.chat.message.Packet;

public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    public byte[] encode(Packet packet) {
        return JSON.toJSONBytes(packet);
    }

    public Packet decode(byte command, byte[] bytes) {
        if (command == Command.LOGIN_REQUEST) {
            return JSON.parseObject(bytes, LoginRequestPacket.class);
        }
        if (command == Command.LOGIN_RESPONSE) {
            return JSON.parseObject(bytes, LoginResponsePacket.class);
        }
        if (command == Command.MESSAGE_REQUEST) {
            return JSON.parseObject(bytes, ChatMessagePacket.class);
        }
        if (command == Command.MESSAGE_RESPONSE) {
            return JSON.parseObject(bytes, ChatMessagePacket.class);
        }
        return null;
    }
}
