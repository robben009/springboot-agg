package com.chat.codec;

import com.chat.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 这个解码器实现解决了 TCP 粘包和拆包问题
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //检查最小长度
        if (in.readableBytes() < 10) {
            return;
        }

        //标记读指针,方便数据不全时回退指针
        in.markReaderIndex();

        //读取4字节（int）魔数，验证协议正确性
        int magic = in.readInt();
        if (magic != 0x12345678) {
            ctx.close();
            return;
        }

        // 读取1字节版本号
        byte version = in.readByte();

        // 读取1字节命令，标识消息类型
        byte command = in.readByte();

        //读取4字节数据长度，告诉后续读取多少数据为完整消息体,如果接收缓冲区剩余可读字节小于长度，说明数据还没到齐，恢复读指针，等待更多数据
        int length = in.readInt();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        //读取指定长度消息体字节数组。
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Packet packet = PacketCodec.INSTANCE.decode(command, bytes);
        if (packet != null) {
            out.add(packet);
        }
    }
}
