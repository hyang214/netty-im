package lesson.ch08.command;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
public interface Command {

    /**
     * 返回命令code
     * @return
     */
    byte getCommandCode();

    /**
     * 获取数据
     * @return
     */
    Object getData();
}
