package com.chat.message;

import com.chat.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsgPacket extends Packet {
    private String fromUser;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
