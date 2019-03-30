package lesson.ch09.command;

import com.google.common.collect.Maps;
import lesson.ch08.command.CommandEnum;
import lesson.ch09.command.handle.RequestCommandHandler;
import lesson.ch09.command.handle.impl.LoginReqHandler;
import lesson.ch10.command.handler.ResponseCommandHandler;
import lesson.ch10.command.handler.impl.LoginResHandler;
import lesson.ch10.command.handler.impl.MessageReqHandler;
import lesson.ch10.command.handler.impl.MessageResHandler;

import java.util.Map;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class CommandHandleRoute {

    private static Map<Byte, RequestCommandHandler> requestHandlerMap;
    private static Map<Byte, ResponseCommandHandler> responseHandlerMap;

    static {
        requestHandlerMap = Maps.newConcurrentMap();
        requestHandlerMap.put(CommandEnum.LOGIN_REQ.getCode(), new LoginReqHandler());
        requestHandlerMap.put(CommandEnum.MESSAGE_REQ.getCode(), new MessageReqHandler());

        responseHandlerMap = Maps.newConcurrentMap();
        responseHandlerMap.put(CommandEnum.LOGIN_RES.getCode(), new LoginResHandler());
        responseHandlerMap.put(CommandEnum.MESSAGE_RES.getCode(), new MessageResHandler());
    }

    /**
     * 获取命令对应的处理器
     * @param commandCode
     * @return
     */
    public static RequestCommandHandler getRequestCommandHandler(byte commandCode) throws Exception {
        RequestCommandHandler handler = requestHandlerMap.get(commandCode);
        if (handler == null) {
            throw new Exception("找不到请求对应处理服务");
        }
        return handler;
    }

    /**
     * 获取命令对应的处理器
     * @param commandCode
     * @return
     */
    public static ResponseCommandHandler getResponseCommandHandler(byte commandCode) throws Exception {
        ResponseCommandHandler handler = responseHandlerMap.get(commandCode);
        if (handler == null) {
            throw new Exception("找不到请求对应处理服务");
        }
        return handler;
    }
}
