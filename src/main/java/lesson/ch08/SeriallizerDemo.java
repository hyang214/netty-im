package lesson.ch08;

import io.netty.buffer.ByteBuf;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.HashingUtils;
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

        PacketV1 origin = getLoginPacket(SerializerEnum.JAVA_DEFAULT);
        log.info("packet: {}", origin);

        ByteBuf byteBuf = ProtocolUtil.encode(origin);

        BasePacket accept = ProtocolUtil.decode(byteBuf);
        log.info("accept: {}", accept);
    }

    /**
     * 获取登录命令
     * @return
     */
    public static PacketV1 getLoginPacket(SerializerEnum type) {
        LoginReq data = new LoginReq();
        data.setUsername("hyang");
        data.setPassword(HashingUtils.sha256("hyang"));

        Command<LoginReq> command = new Command<>(CommandEnum.LOGIN_REQ.getCode(), data);

        PacketV1 packet = new PacketV1();
        packet.setSerializerType(type.getType());
        packet.setCommand(command);

        return packet;
    }


}
