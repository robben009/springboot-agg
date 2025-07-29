package com.chat.message;

import com.chat.Command;

public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    public LoginResponsePacket() {
    }

    public LoginResponsePacket(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
