package lesson.ch02;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.02
 */
@Slf4j
public class MyIOClient {

    private final static String SERVER = "127.0.0.1";
    private final static int PORT = 8000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER, PORT);
            while(true) {
                String data = (new Date() + ": hello world");
                log.info("client send data: {}", data);
                OutputStream output = socket.getOutputStream();
                output.write(data.getBytes());
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            log.error("IOClient error: {}", e);
        }
    }
}
