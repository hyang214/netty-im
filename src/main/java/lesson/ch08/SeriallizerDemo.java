package lesson.ch08;

import com.sun.tools.jdi.Packet;
import io.netty.buffer.ByteBuf;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.Login;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.ProtocolUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.25
 */
@Slf4j
public class SeriallizerDemo {

    /**
     * 1. 创建 登录请求
     * 2. 转换成字节流，写入 ByteBuf
     * 3. 从 ByteBuf 读取数据
     * 4. 解析请求
     * @param args
     */
    public static void main(String[] args) throws Exception {
        PacketV1 origin = getLoginPacket();
        log.info("packet: {}", origin);

        ByteBuf byteBuf = ProtocolUtil.encode(origin);

        BasePacket accept = ProtocolUtil.decode(byteBuf);
        log.info("accept: {}", accept);
    }

    /**
     * 获取登录命令
     * @return
     */
    private static PacketV1 getLoginPacket() {
        Login data = new Login();
        data.setUsername("hyang");
        data.setPassword("password");

        Command<Login> command = new Command<>(CommandEnum.LOGIN.getCode(), data);

        PacketV1 packet = new PacketV1();
        packet.setSerializerType(SerializerEnum.JSON.getType());
        packet.setCommand(command);

        return packet;
    }


}
