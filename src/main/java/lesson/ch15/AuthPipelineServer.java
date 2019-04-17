package lesson.ch15;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lesson.ch12.pipeline.PacketDecoder;
import lesson.ch12.pipeline.PacketEncoder;
import lesson.ch12.pipeline.ServerCommandHandler;
import lesson.ch15.pipeline.AuthHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.19
 */
@Slf4j
public class AuthPipelineServer {

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
                .attr(AttributeKey.newInstance("serverName"), AuthPipelineServer.class.getSimpleName())
                .option(ChannelOption.SO_BACKLOG, 1024)
                /** children 相关 **/
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new ServerCommandHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                })
                .childAttr(AttributeKey.newInstance("childServerName"), AuthPipelineServer.class.getSimpleName() + "-child")
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
