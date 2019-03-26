package lesson.ch08.packet;

import lesson.ch08.command.Command;
import lesson.ch08.serializer.Serializer;
import lesson.ch08.serializer.SerializerEnum;
import lombok.Data;
import lombok.ToString;

/**
 * title: 抽象 数据包
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
@ToString
public class PacketV1 extends BasePacket {
    /**
     * 协议版本
     */
    private final byte VERSION = 1;
    /**
     * 序列化方法
     */
    private byte serializerType;
    /**
     * 命令
     */
    private Command command;

    /**
     * 获取数据
     * @return
     */
    @Override
    public byte[] getData() throws Exception {
        SerializerEnum e = SerializerEnum.getByType(this.serializerType);
        Serializer serializer = e.getClazz().newInstance();
        return serializer.serialize(command.getData());
    }

    @Override
    public byte getCommandCode() {
        return command.getCommandCode();
    }

    @Override
    public byte getVersion() {
        return VERSION;
    }
}
