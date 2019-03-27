package lesson.ch09.command.handle.impl;

import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch08.utils.HashingUtils;
import lesson.ch09.command.impl.LoginRes;
import lesson.ch09.command.ResponseStatus;
import lesson.ch09.command.handle.CommandHandler;

/**
 * title: 响应登录
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class LoginHandler implements CommandHandler {

    @Override
    public Command handle(Command command) {
        LoginReq cat = (LoginReq) command.getData();
        String username = cat.getUsername();
        String check = HashingUtils.sha256(username);
        LoginRes loginRes = new LoginRes();
        if (check.equals(cat.getPassword())) {
            loginRes.setStatus(ResponseStatus.SUCCESS.getCode());
            loginRes.setMsg("登录成功");
        } else {
            loginRes.setStatus(ResponseStatus.FAIL.getCode());
            loginRes.setMsg("密码错误");
        }

        Command<LoginRes> res = new Command<>(CommandEnum.LOGIN_RES.getCode(), loginRes);
        return res;
    }
}
