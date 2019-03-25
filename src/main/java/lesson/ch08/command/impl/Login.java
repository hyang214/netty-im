package lesson.ch08.command.impl;

import lesson.ch08.command.CommandData;
import lombok.Data;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
public class Login extends CommandData {

    private String username;

    private String encodedPW;

}
