package lesson.ch12.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandData;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.Serializer;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch09.command.impl.LoginRes;
import lesson.ch10.command.impl.MessageReq;
import lesson.ch10.command.impl.MessageRes;
import lombok.extern.slf4j.Slf4j;

/**
 * title: 相比v1，简化了 parseV1 的方法
 *
 * @author Hao YANG
 * @since 2019.03.25
 */
@Slf4j
public class ProtocolUtilV2 {

    /**
     * 解析
     * @param byteBuf
     * @return
     * @throws Exception
     */
    public static BasePacket decode(ByteBuf byteBuf) throws Exception {
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
    public static ByteBuf encode(BasePacket packet) throws Exception {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        /** 写 magic number **/
        byteBuf.writeInt(BasePacket.MAGIC_NUMBER);

        /** 写版本 **/
        byteBuf.writeByte(packet.getVersion());

        /** 写序列化算法 **/
        byteBuf.writeByte(packet.getSerializerType());

        /** 写入命令 **/
        byteBuf.writeByte(packet.getCommandCode());

        /** 获取数据 **/
        byte[] data = packet.getData();
        int length = data.length;

        /** 写数据长度 **/
        byteBuf.writeInt(length);

        /** 写数据 **/
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    /**
     * 按照 version = 1的结构进行解析
     * @param byteBuf
     */
    private static PacketV1 parseV1(ByteBuf byteBuf) throws Exception {
        /** 序列化算法标识 **/
        byte serializeAlgorithm = byteBuf.readByte();

        /** 构造序列化算法 **/
        SerializerEnum e = SerializerEnum.getByType(serializeAlgorithm);
        Serializer serializer = e.getClazz().newInstance();

        /** 获取命令 **/
        byte commandCode = byteBuf.readByte();

        /** 数据包长度 **/
        int length = byteBuf.readInt();

        /** 读取数据 **/
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        CommandEnum ce = CommandEnum.getByCode(commandCode);
        if (ce != null) {
            CommandData data = serializer.deserialize(ce.getClazz(), bytes);
            Command command = new Command<>(commandCode, data);

            PacketV1 packetV1 = new PacketV1();
            packetV1.setCommand(command);
            return packetV1;
        } else {
            throw new Exception("未知命令");
        }
    }

}
