package lesson.ch08.command.impl;

import lesson.ch08.command.CommandData;
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
public class LoginReq extends CommandData implements Serializable {

    private final static long serialVersionUID = 1L;

    private String username;

    private String password;
}
