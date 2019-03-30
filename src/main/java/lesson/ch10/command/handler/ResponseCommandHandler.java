package lesson.ch10.command.handler;

import io.netty.channel.Channel;
import lesson.ch08.command.Command;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
public interface ResponseCommandHandler {

    /**
     * 处理命令，并且返回响应
     *
     * @param command
     * @param channel
     * @return
     */
    Command handle(Command command, Channel channel);
}
