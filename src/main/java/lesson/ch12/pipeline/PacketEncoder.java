package lesson.ch12.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lesson.ch08.packet.BasePacket;
import lesson.ch12.utils.ProtocolUtilV2;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.02
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {
        ByteBuf byteBuf = ProtocolUtilV2.encode(msg);
        out.writeBytes(byteBuf);
    }

}
