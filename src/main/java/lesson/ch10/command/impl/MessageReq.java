package lesson.ch10.command.impl;

import lesson.ch08.command.CommandData;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * title: 消息请求
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
@Data
@ToString
public class MessageReq extends CommandData implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 消息
     */
    private String message;
}
