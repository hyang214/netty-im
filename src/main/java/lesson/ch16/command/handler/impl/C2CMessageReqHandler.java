package lesson.ch16.command.handler.impl;

import io.netty.channel.Channel;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch09.command.handle.RequestCommandHandler;
import lesson.ch10.utils.LoginUtil;
import lesson.ch16.command.impl.C2CMessageDelivery;
import lesson.ch16.command.impl.C2CMessageReq;
import lesson.ch16.command.impl.C2CMessageRes;
import lombok.extern.slf4j.Slf4j;


/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.04.18
 */
@Slf4j
public class C2CMessageReqHandler implements RequestCommandHandler {

    @Override
    public Command handle(Command command) {
        C2CMessageReq data = (C2CMessageReq) command.getData();

        C2CMessageRes res = new C2CMessageRes();

        String errorMsg = sendMessage(data);
        if (errorMsg == null) {
            res.setSuccess(true);
            res.setMessage("发送成功");
        } else {
            res.setSuccess(false);
            res.setMessage(errorMsg);
        }

        Command<C2CMessageRes> response = new Command<>(CommandEnum.C2C_MESSAGE_RES.getCode(), res);
        return response;
    }

    private String sendMessage(C2CMessageReq data) {
        try {
            Channel toUserChannel = LoginUtil.getTargetChannel(data.getToUsername());
            if (toUserChannel == null) {
                return "目标用户未登录";
            }

            C2CMessageDelivery delivery = new C2CMessageDelivery();
            delivery.setFromUsername(data.getFromUsername());
            delivery.setToUsername(data.getToUsername());
            delivery.setMessage(data.getMessage());

            Command<C2CMessageDelivery> response = new Command<>(CommandEnum.C2C_MESSAGE_DELIVERY.getCode(), delivery);

            toUserChannel.writeAndFlush(response);

            return null;
        } catch (Exception e) {
            log.error("C2CMessageReqHandler->sendMessage error: {}", e);
            return "发送异常";
        }
    }

}
