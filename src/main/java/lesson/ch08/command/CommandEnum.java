package lesson.ch08.command;

import lombok.Getter;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Getter
public enum CommandEnum {
    LOGIN_REQ((byte) 1, "登录请求"),
    LOGIN_RES((byte) 2, "登录响应"),
    ;

    private byte code;
    private String name;

    CommandEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
