package lesson.ch08.command;

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
public class Command<T extends CommandData> {

    /**
     * 命令
     */
    private byte commandCode;
    /**
     * 数据
     */
    private T data;

    public Command(byte commandCode, T data) {
        this.commandCode = commandCode;
        this.data = data;
    }
}
