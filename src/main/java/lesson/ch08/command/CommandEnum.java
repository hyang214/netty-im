package lesson.ch08.command;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

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

    private static Set<Byte> commandSet;

    static {
        commandSet = Sets.newHashSet();
        Arrays.stream(CommandEnum.values()).forEach((x)->commandSet.add(x.code));
    }

    private byte code;
    private String name;

    CommandEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 是否包含对应命令
     * @param code
     * @return
     */
    public static boolean contains(byte code) {
        return commandSet.contains(code);
    }
}
