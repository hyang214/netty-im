package lesson.ch11.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.31
 */
public class InboundHandlerA extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InboundHandlerA: " + msg);
        super.channelRead(ctx, msg);
    }

}
