package com.chat.codec;

import com.chat.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 10) {
            return; // 不够基础长度
        }

        in.markReaderIndex();
        int magic = in.readInt();
        if (magic != 0x12345678) {
            ctx.close();
            return;
        }
        in.readByte(); // version
        byte command = in.readByte();
        int length = in.readInt();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Packet packet = PacketCodec.INSTANCE.decode(command, bytes);
        if (packet != null) {
            out.add(packet);
        }
    }
}
