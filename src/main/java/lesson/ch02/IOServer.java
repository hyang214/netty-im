package lesson.ch02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.02
 */
@Slf4j
public class IOServer {

    private final static int PORT = 8000;

    public static void main(String[] args) throws IOException {
        // 监听的端口
        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            log.info("IOServer start at port " + PORT);
            while(true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    new Thread(()-> {
                        try {
                            /** 读取数据，并处理 **/
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println("request: " + new String(data, 0, len));
                            }
                        } catch (Exception e) {
                            log.error("IOServer handle error: {}", e);
                        }
                    }).start();
                } catch (Exception e) {
                    log.error("IOServer error: {}", e);
                }
            }
        } catch (Exception e) {
            log.error("IOServer error: {}", e);
        }
    }
}
