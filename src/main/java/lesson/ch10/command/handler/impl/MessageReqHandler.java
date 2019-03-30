package lesson.ch10.command.handler.impl;

import com.google.common.base.Strings;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch09.command.ResponseStatus;
import lesson.ch09.command.handle.RequestCommandHandler;
import lesson.ch10.command.impl.MessageRes;

import java.util.Scanner;

/**
 * title: 响应消息发送
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class MessageReqHandler implements RequestCommandHandler {

    @Override
    public Command handle(Command command) {

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        MessageRes data = new MessageRes();
        if (Strings.isNullOrEmpty(line)) {
            data.setMessage(line);
            data.setStatus(ResponseStatus.FAIL.getCode());
            data.setMsg("获取消息失败");
        } else {
            data.setMessage(line);
            data.setStatus(ResponseStatus.SUCCESS.getCode());
            data.setMsg("获取消息成功");
        }

        Command res = new Command<>(CommandEnum.MESSAGE_RES.getCode(), data);
        return res;
    }
}
