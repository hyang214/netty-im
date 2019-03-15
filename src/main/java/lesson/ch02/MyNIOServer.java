package lesson.ch02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.15
 */
@Slf4j
public class MyNIOServer {

    public static void main(String[] args) throws Exception {
        /** 创建 channel，用于监听新增连接
         * acceptChannel: ServerSocketChannel 只能处理OP_ACCEPT事件，详见 {@link ServerSocketChannel#validOps()}
         * **/
        ServerSocketChannel acceptChannel = ServerSocketChannel.open();
        acceptChannel.socket().bind(new InetSocketAddress(8000));
        acceptChannel.configureBlocking(false);

        /** 创建 selector
         * acceptSelector: 用于轮训新建请求
         * handleSelector：用于轮训数据读取
         * **/
        Selector acceptSelector = Selector.open();
        Selector handleSelector = Selector.open();

        /** 将 channel 注册到 selector 上，监听  **/
        acceptChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);

        /** 请求接受线程 **/
        new Thread(() -> {
            try {
                while(true) {
                    while (acceptSelector.select(1) > 0 ){
                        for (SelectionKey selectionKey : acceptSelector.selectedKeys()) {
                            if (selectionKey.isAcceptable()) {
                                SocketChannel clientChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                clientChannel.configureBlocking(false);
                                clientChannel.register(handleSelector, SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("e: {}", e);
            }
        }).start();

        /** 数据接受线程 **/
        new Thread(() -> {
            try {
                while(true) {
                    while (handleSelector.select(1) > 0 ){
                        for (SelectionKey selectionKey : handleSelector.selectedKeys()) {
                            if (selectionKey.isReadable()) {
                                receive(selectionKey);
                            }
                        }
                        /** 由于selectedKeys返回的是一个固定的集合，因此，在每次处理了之后，必须手动清空 **/
                        handleSelector.selectedKeys().clear();
                    }
                }
            } catch (Exception e) {
                log.error("e: {}", e);
            }
        }).start();
    }


    private static void receive(SelectionKey selectionKey) throws IOException {
        SocketChannel tmp = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        tmp.read(buffer);
        buffer.flip();
        String receiveData = Charset.forName("UTF-8").decode(buffer).toString();
        log.info(receiveData);
    }
}
