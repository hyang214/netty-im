package lesson.ch12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
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
 * @since 2019.03.19
 */
@Slf4j
public class PipelineServer {

    public static void main(String[] args) {

        NioEventLoopGroup parent = new NioEventLoopGroup();
        NioEventLoopGroup children = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(parent, children)
                .channel(NioServerSocketChannel.class)
                /** parent 相关 **/
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        log.info("parent initChannel ch: {}", ch.id());
                    }
                })
                .attr(AttributeKey.newInstance("serverName"), PipelineServer.class.getSimpleName())
                .option(ChannelOption.SO_BACKLOG, 1024)
                /** children 相关 **/
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                BasePacket req = ProtocolUtil.decode(byteBuf);
                                log.info(new Date() + ": 服务端读到数据: {} " + req);

                                RequestCommandHandler handler = CommandHandleRoute.getRequestCommandHandler(req.getCommandCode());
                                Command handleResult = handler.handle(req.getCommand(), ch);

                                PacketV1 res = new PacketV1();
                                res.setSerializerType(SerializerEnum.JSON.getType());
                                res.setCommand(handleResult);

                                ByteBuf resBuf = ProtocolUtil.encode(res);
                                ByteBuf response = ctx.alloc().buffer();
                                response.writeBytes(resBuf);
                                ctx.channel().writeAndFlush(response);
                            }

                        });
                    }
                })
                .childAttr(AttributeKey.newInstance("childServerName"), PipelineServer.class.getSimpleName() + "-child")
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bind(serverBootstrap);
    }

    private static int port = 8090;
    private static void bind(ServerBootstrap serverBootstrap) {
        serverBootstrap.bind(port).addListener((future)->{
            if (future.isSuccess()) {
                log.info("bind success port: {}", port);
            } else {
                log.info("bind failure port: {}", port);
                port++;
                bind(serverBootstrap);
            }
        });
    }
}
