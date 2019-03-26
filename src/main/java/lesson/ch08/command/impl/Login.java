package lesson.ch08.command.impl;

import lesson.ch08.command.CommandData;
import lesson.ch08.utils.HashingUtils;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
@ToString
public class Login extends CommandData implements Serializable {

    private final static long serialVersionUID = 1L;

    private String username;

    private String password;

    public void setPassword(String password) {
        this.password = HashingUtils.sha256(password);
    }
}
