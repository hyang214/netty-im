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
    /** ch08 **/
    LOGIN_REQ((byte) 1, "登录请求"),
    /** ch09 **/
    LOGIN_RES((byte) 2, "登录响应"),
    /** ch10 **/
    MESSAGE_REQ((byte) 3, "消息请求"),
    MESSAGE_RES((byte) 4, "消息返回"),
    ;

    private byte code;
    private String name;

    CommandEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
