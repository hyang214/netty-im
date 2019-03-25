package lesson.ch08;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.Login;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.Serializer;
import lesson.ch08.serializer.SerializerEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.25
 */
@Slf4j
public class ProtocolUtil {

    /**
     * 解析
     * @param byteBuf
     * @return
     * @throws Exception
     */
    public BasePacket decode(ByteBuf byteBuf) throws Exception {
        /** 读取 magic number **/
        int magicNumber = byteBuf.readInt();
        if (magicNumber != BasePacket.MAGIC_NUMBER){
            /** 非本协议，不解析 **/
            return null;
        }

        /** 版本 **/
        byte version = byteBuf.readByte();
        if (version == 1) {
            return parseV1(byteBuf);
        } else {
            throw new Exception("未知协议版本");
        }
    }

    /**
     * 反解析
     * @param packet
     * @return
     */
    public ByteBuf encode(BasePacket packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();


    }

    /**
     * 按照 version = 1的结构进行解析
     * @param byteBuf
     */
    private PacketV1 parseV1(ByteBuf byteBuf) throws Exception {
        /** 序列化算法标识 **/
        byte serializeAlgorithm = byteBuf.readByte();

        /** 构造序列化算法 **/
        SerializerEnum e = SerializerEnum.getByType(serializeAlgorithm);
        Serializer serializer = e.getClazz().newInstance();

        /** 数据包长度 **/
        int length = byteBuf.readInt();

        /** 读取数据 **/
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        /** 数据反序列化 **/
        Command command = serializer.deserialize(Command.class, bytes);

        /** 获取命令 **/
        byte commandCode = command.getCommandCode();
        if (commandCode == CommandEnum.LOGIN.getCode()) {
            Login data = (Login) command.getData();
            log.info("data: {}", JSONObject.toJSONString(data));

        } else {
            throw new Exception("未知命令");
        }

        PacketV1 packetV1 = new PacketV1();
        packetV1.setCommand(command);
        return packetV1;
    }
}
