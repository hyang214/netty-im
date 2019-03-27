package lesson.ch09.command;

import com.google.common.collect.Maps;
import lesson.ch08.command.CommandEnum;
import lesson.ch09.command.handle.CommandHandler;
import lesson.ch09.command.handle.impl.LoginHandler;

import java.util.Map;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class CommandHandleRoute {

    private static Map<Byte, CommandHandler> handlerMap;

    static {
        handlerMap = Maps.newConcurrentMap();
        handlerMap.put(CommandEnum.LOGIN_REQ.getCode(), new LoginHandler());
    }

    /**
     * 获取命令对应的处理器
     * @param commandCode
     * @return
     */
    public static CommandHandler getComandHandler(byte commandCode) throws Exception {
        CommandHandler handler = handlerMap.get(commandCode);
        if (handler == null) {
            throw new Exception("找不到请求对应处理服务");
        }
        return handler;
    }

}
