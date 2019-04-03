package lesson.ch12.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lesson.ch08.SeriallizerDemo;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.ProtocolUtil;
import lesson.ch09.command.CommandHandleRoute;
import lesson.ch10.command.handler.ResponseCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.03
 */
@Slf4j
public class ClientCommandHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PacketV1 loginReq = SeriallizerDemo.getLoginPacket(SerializerEnum.JSON);
        log.info(new Date() + " client write data: {}", loginReq);
        // 输出
        ctx.channel().writeAndFlush(loginReq);
    }

    /**
     * ByteBuf 在 {@link PacketDecoder} 中会被解析
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasePacket res = (BasePacket) msg;
        Byte commandCode = res.getCommandCode();
        ResponseCommandHandler handler = CommandHandleRoute.getResponseCommandHandler(commandCode);
        handler.handle(res.getCommand(), ctx.channel());
    }

}
