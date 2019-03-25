package lesson.ch08.command;

import lombok.Data;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
public class Command<T extends CommandData> {

    /**
     * 命令
     */
    private byte commandCode;
    /**
     * 数据
     */
    private T data;

}
