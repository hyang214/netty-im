package lesson.ch12.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lesson.ch12.utils.ProtocolUtilV2;

import java.util.List;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.01
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(ProtocolUtilV2.decode(in));
    }

}
