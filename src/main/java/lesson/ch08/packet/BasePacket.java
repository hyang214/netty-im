package lesson.ch08.packet;

import lesson.ch08.command.Command;
import lombok.Data;
import lombok.ToString;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
@ToString
public abstract class BasePacket {

    /**
     * magic number: 用于区分协议
     */
    public final static int MAGIC_NUMBER = 0x19910113;

    /**
     * 获取版本号
     * @return
     */
    public abstract byte getVersion();

    /**
     * 获取数据
     * @return
     * @throws Exception
     */
    public abstract byte[] getData() throws Exception;

    /**
     * 获取序列化算法
     * @return
     */
    public abstract byte getSerializerType();

    /**
     * 获取命令
     * @return
     */
    public abstract byte getCommandCode();

    /**
     * 获取命令
     * @return
     */
    public abstract Command getCommand();
}
