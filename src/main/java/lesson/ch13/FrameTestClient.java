package lesson.ch13;

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
public class FrameTestClient {

    private final static Integer TEST_CASE_NUM = 100;

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
                            public void channelActive(ChannelHandlerContext ctx) {
                                for (int i = 0; i < TEST_CASE_NUM; i ++) {
                                    log.info(new Date() + " client write data");
                                    // 写数据
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    byte[] data = "Hello World, test data frame need a long sentence, so I write a long sentence!".getBytes(Charset.forName("utf-8"));
                                    int length = data.length;
                                    buffer.writeInt(length);
                                    buffer.writeBytes(data);
                                    // 输出
                                    ctx.channel().writeAndFlush(buffer);
                                }
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                ByteBuf response = (ByteBuf) msg;
                                log.info(new Date() + ": client receive data from server -> " + response.toString(Charset.forName("utf-8")));
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
