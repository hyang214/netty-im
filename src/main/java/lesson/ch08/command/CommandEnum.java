package lesson.ch08.command;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch09.command.impl.LoginRes;
import lesson.ch10.command.impl.MessageReq;
import lesson.ch10.command.impl.MessageRes;
import lesson.ch16.command.impl.C2CMessageDelivery;
import lesson.ch16.command.impl.C2CMessageReq;
import lesson.ch16.command.impl.C2CMessageRes;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
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
    LOGIN_REQ((byte) 1, "登录请求", LoginReq.class),
    /** ch09 **/
    LOGIN_RES((byte) 2, "登录响应", LoginRes.class),
    /** ch10 **/
    MESSAGE_REQ((byte) 3, "消息请求", MessageReq.class),
    MESSAGE_RES((byte) 4, "消息返回", MessageRes.class),
    /** ch16 **/
    C2C_MESSAGE_REQ((byte) 5, "用户间消息发送请求", C2CMessageReq.class),
    C2C_MESSAGE_RES((byte) 6, "用户间消息发送响应", C2CMessageRes.class),
    C2C_MESSAGE_DELIVERY((byte) 7, "用户间消息发送投递", C2CMessageDelivery.class);
    ;

    private static Set<Byte> commandSet;
    private static Map<Byte, CommandEnum> commandEnumMap;

    static {
        commandSet = Sets.newHashSet();
        commandEnumMap = Maps.newHashMap();
        Arrays.stream(CommandEnum.values()).forEach((x)->{
            commandSet.add(x.code);
            commandEnumMap.put(x.getCode(), x);
        });
    }

    private byte code;
    private String name;
    private Class<? extends CommandData> clazz;

    CommandEnum(byte code, String name, Class clazz) {
        this.code = code;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 是否包含对应命令
     * @param code
     * @return
     */
    public static boolean contains(byte code) {
        return commandSet.contains(code);
    }

    public static CommandEnum getByCode(Byte code) {
        return commandEnumMap.get(code);
    }
}
