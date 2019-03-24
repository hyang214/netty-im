package lesson.ch08.command.impl;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lombok.Data;

import java.util.Map;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
public class Login implements Command {

    private String username;

    private String password;

    private String encodedPW;

    @Override
    public byte getCommandCode() {
        return CommandEnum.LOGIN.getCode();
    }

    @Override
    public Object getData() {
        Map<String, String> map = Maps.newHashMap();
        map.put("username", username);
        map.put("encodedPW", md5Encode(this.password));
        return null;
    }

    private String md5Encode(String password) {
        return Hashing.md5().hashBytes(password.getBytes()).toString();
    }
}
