package com.chat.message;

import com.chat.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusMsgPacket extends Packet {

    private String loginMsg;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_STATUS;
    }

}
