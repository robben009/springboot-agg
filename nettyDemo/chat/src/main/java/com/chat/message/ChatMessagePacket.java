package com.chat.message;

import com.chat.Command;

public class ChatMessagePacket extends Packet {
    private String fromUser;
    private String message;

    public ChatMessagePacket() {
    }

    public ChatMessagePacket(String message) {
        this.message = message;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
