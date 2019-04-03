package lesson.ch12.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lesson.ch08.command.Command;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.ProtocolUtil;
import lesson.ch09.command.CommandHandleRoute;
import lesson.ch09.command.handle.RequestCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.03
 */
@Slf4j
public class ServerCommandHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasePacket req = (BasePacket) msg;
        log.info(new Date() + ": 服务端读到数据: {} " + req);

        RequestCommandHandler handler = CommandHandleRoute.getRequestCommandHandler(req.getCommandCode());
        Command handleResult = handler.handle(req.getCommand(), ctx.channel());
        PacketV1 res = new PacketV1();
        res.setSerializerType(SerializerEnum.JSON.getType());
        res.setCommand(handleResult);

        ctx.channel().writeAndFlush(res);
    }

}
