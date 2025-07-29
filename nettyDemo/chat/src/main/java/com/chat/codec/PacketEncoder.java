package com.chat.codec;

import com.chat.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        byte[] data = PacketCodec.INSTANCE.encode(packet);
        out.writeInt(0x12345678); // magic
        out.writeByte(1); // version
        out.writeByte(packet.getCommand());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}

