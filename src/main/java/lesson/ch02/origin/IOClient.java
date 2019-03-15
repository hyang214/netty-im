package lesson.ch02.origin;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.02
 */
public class IOClient {

    private static Integer count = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world count " + count).getBytes());
                        Thread.sleep(2000);
                        socket.getOutputStream().write((new Date() + ": hello world count " + count).getBytes());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
