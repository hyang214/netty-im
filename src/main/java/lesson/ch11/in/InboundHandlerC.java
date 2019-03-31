package lesson.ch11.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.31
 */
public class InboundHandlerC extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InboundHandlerC: " + msg);
        super.channelRead(ctx, msg);
    }

}
