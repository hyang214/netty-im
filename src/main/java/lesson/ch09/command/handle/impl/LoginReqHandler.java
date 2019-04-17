package lesson.ch09.command.handle.impl;

import io.netty.channel.Channel;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch08.utils.HashingUtils;
import lesson.ch09.command.ResponseStatus;
import lesson.ch09.command.handle.RequestCommandHandler;
import lesson.ch09.command.impl.LoginRes;
import lesson.ch10.user.UserInfo;
import lesson.ch10.utils.LoginUtil;

/**
 * title: 响应登录
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class LoginReqHandler implements RequestCommandHandler {

    @Override
    public Command handle(Command command) {
        LoginReq cat = (LoginReq) command.getData();
        String username = cat.getUsername();
        String check = HashingUtils.sha256(username);
        LoginRes loginRes = new LoginRes();
        loginRes.setUsername(cat.getUsername());
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

    /**
     * 新增用户信息标记，用于记录登录态
     * @param command
     * @param channel
     * @return
     */
    @Override
    public Command handle(Command command, Channel channel) {
        Command<LoginRes> res = handle(command);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(res.getData().getUsername());
        LoginUtil.markAsLogin(channel, userInfo);
        return res;
    }
}
