package lesson.ch09.command.handle;

import io.netty.channel.Channel;
import lesson.ch08.command.Command;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public interface RequestCommandHandler {

    /**
     * 处理命令，并且返回响应
     * @param command
     * @return
     */
    Command handle(Command command);


    /**
     * 处理命令，并且返回响应
     * ch10 需要新增
     * @param command
     * @param channel
     * @return
     */
    default Command handle(Command command, Channel channel) {
        return this.handle(command);
    }
}
