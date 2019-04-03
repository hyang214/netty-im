package lesson.ch13;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.19
 */
@Slf4j
public class FrameServer {

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
                .attr(AttributeKey.newInstance("serverName"), FrameServer.class.getSimpleName())
                .option(ChannelOption.SO_BACKLOG, 1024)
                /** children 相关 **/
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.info(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

                                ByteBuf response = ctx.alloc().buffer();
                                response.writeBytes("Hi, Siri".getBytes(Charset.forName("utf-8")));
                                ctx.channel().writeAndFlush(response);
                            }

                        });
                    }
                })
                .childAttr(AttributeKey.newInstance("childServerName"), FrameServer.class.getSimpleName() + "-child")
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
