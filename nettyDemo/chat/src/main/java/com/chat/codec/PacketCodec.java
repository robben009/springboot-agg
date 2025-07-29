package com.chat.codec;

import com.alibaba.fastjson2.JSON;
import com.chat.Command;
import com.chat.message.*;

public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    public byte[] encode(Packet packet) {
        return JSON.toJSONBytes(packet);
    }

    public Packet decode(byte command, byte[] bytes) {
        if (command == Command.LOGIN_REQUEST) {
            return JSON.parseObject(bytes, LoginReqPacket.class);
        }
        if (command == Command.LOGIN_RESPONSE) {
            return JSON.parseObject(bytes, LoginRespPacket.class);
        }
        if (command == Command.MESSAGE_REQUEST) {
            return JSON.parseObject(bytes, ChatMsgPacket.class);
        }
        if (command == Command.MESSAGE_RESPONSE) {
            return JSON.parseObject(bytes, ChatMsgPacket.class);
        }
        if (command == Command.LOGIN_STATUS) {
            return JSON.parseObject(bytes, StatusMsgPacket.class);
        }

        return null;
    }
}
