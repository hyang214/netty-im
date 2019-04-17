package lesson.ch15;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lesson.ch12.pipeline.ClientCommandHandler;
import lesson.ch12.pipeline.PacketDecoder;
import lesson.ch12.pipeline.PacketEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.19
 */
@Slf4j
public class AuthPipelineClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ClientCommandHandler());
                        ch.pipeline().addLast(new PacketEncoder());
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
