package lesson.ch09.command;

import lesson.ch08.command.CommandData;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * title: 基础相应类
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
@Data
@ToString
public class BaseResponse extends CommandData implements Serializable {

    /**
     * 状态码
     * {@link ResponseStatus}
     */
    private byte status;
    /**
     * 消息
     */
    private String msg;
}
