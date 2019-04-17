package lesson.ch15.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.packet.BasePacket;
import lesson.ch10.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.18
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    /**
     * ByteBuf
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasePacket req = (BasePacket) msg;
        if (req.getCommandCode() == CommandEnum.LOGIN_REQ.getCode()) {
            super.channelRead(ctx, msg);
        } else {
            if (LoginUtil.hasLogin(ctx.channel())) {
                removeAuthHandler(ctx);
                super.channelRead(ctx, msg);
            } else {
                ctx.channel().close();
            }
        }
    }

    private void removeAuthHandler(ChannelHandlerContext ctx) {
        log.info("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        ctx.pipeline().remove(this);
    }

}
