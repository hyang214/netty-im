package lesson.ch02;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.15
 */
@Slf4j
public class MyNIOServer {

    public static void main(String[] args) throws Exception {
        /** 创建 selector **/
        Selector selector = Selector.open();

        /** 创建 channel **/
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8000));
        channel.configureBlocking(false);

        /** 将 channel 注册到 selector 上，监听  **/
        int set = SelectionKey.OP_ACCEPT | SelectionKey.OP_READ;
        channel.register(selector, set);

        new Thread(() -> {
            try {
                while(true) {
                    while (selector.select() > 0 ){
                        for (SelectionKey selectionKey : selector.selectedKeys()) {
                            if (selectionKey.isAcceptable()) {
                                log.info("isAcceptable ");
                            } else if (selectionKey.isReadable()) {
                                SocketChannel tmp = (SocketChannel) selectionKey.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                tmp.read(buffer);
                                buffer.flip();
                                String receiveData= Charset.forName("UTF-8").decode(buffer).toString();
                                log.info(receiveData);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("e: {}", e);
            }
        }).start();
    }
}
