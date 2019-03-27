package lesson.ch09.command.impl;

import lesson.ch09.command.BaseResponse;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * title: 登录返回
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
@Data
@ToString
public class LoginRes extends BaseResponse implements Serializable {

    private final static long serialVersionUID = 1L;

}
