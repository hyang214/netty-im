package lesson.ch09.command;

import lombok.Getter;

/**
 * title: 返回状态
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
@Getter
public enum ResponseStatus {

    SUCCESS((byte) 0, "success"),
    FAIL((byte) 1, "fail"),
    ;

    private byte code;
    private String name;

    ResponseStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }

}
