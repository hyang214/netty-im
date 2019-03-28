package lesson.ch09.command.handle;

import lesson.ch08.command.Command;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public interface CommandHandler {

    /**
     * 处理命令，并且返回响应
     * @param command
     * @return
     */
    Command handle(Command command);
}
