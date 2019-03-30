package lesson.ch10;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lesson.ch08.SeriallizerDemo;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.ProtocolUtil;
import lesson.ch09.command.CommandHandleRoute;
import lesson.ch09.command.handle.RequestCommandHandler;
import lesson.ch10.command.handler.ResponseCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.19
 */
@Slf4j
public class MessageClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                PacketV1 loginReq = SeriallizerDemo.getLoginPacket(SerializerEnum.JSON);
                                log.info(new Date() + " client write data: {}", loginReq);

                                ByteBuf byteBuf = ProtocolUtil.encode(loginReq);
                                // 写数据
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes(byteBuf);
                                // 输出
                                ctx.channel().writeAndFlush(buffer);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf response = (ByteBuf) msg;
                                BasePacket res = ProtocolUtil.decode(response);

                                Byte commandCode = res.getCommandCode();
                                ResponseCommandHandler handler = CommandHandleRoute.getResponseCommandHandler(commandCode);

                                handler.handle(res.getCommand(), ch);
                            }

                        });
                    }
                })
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

        bootstrap.connect("127.0.0.1", 8090).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功!");
            } else {
                log.info("连接失败!");
            }
        });
    }
}
