package lesson.ch16.command.impl;

import lesson.ch09.command.BaseResponse;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * title: 消息返回
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
@Data
@ToString
public class C2CMessageRes extends BaseResponse implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 消息
     */
    private String message;

    private Boolean success;
}
