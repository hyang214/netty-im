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
    LOGIN((byte) 1, "登录"),
    ;

    private byte code;
    private String name;

    CommandEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
