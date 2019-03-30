package lesson.ch10.command.handler.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lesson.ch08.command.Command;
import lesson.ch08.command.CommandEnum;
import lesson.ch08.command.impl.LoginReq;
import lesson.ch08.packet.BasePacket;
import lesson.ch08.packet.PacketV1;
import lesson.ch08.serializer.SerializerEnum;
import lesson.ch08.utils.ProtocolUtil;
import lesson.ch09.command.ResponseStatus;
import lesson.ch09.command.impl.LoginRes;
import lesson.ch10.command.handler.ResponseCommandHandler;
import lesson.ch10.command.impl.MessageReq;
import lesson.ch10.user.UserInfo;
import lesson.ch10.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Scanner;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
@Slf4j
public class LoginResHandler implements ResponseCommandHandler {


    @Override
    public Command handle(Command command, Channel channel) {
        log.info(new Date() + ": client receive data from server: {} ", command.getData());
        Command<LoginRes> res = command;
        if (res.getData().getStatus() == ResponseStatus.SUCCESS.getCode()) {
            LoginRes cat = (LoginRes) command.getData();

            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(cat.getUsername());
            LoginUtil.markAsLogin(channel, userInfo);
        }
        startConsoleThread(channel);
        return null;
    }

    private void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    if (LoginUtil.hasLogin(channel)) {
                        log.info("print info to server: ");
                        Scanner sc = new Scanner(System.in);
                        String line = sc.nextLine();

                        MessageReq data = new MessageReq();
                        data.setMessage(line);

                        PacketV1 packet = new PacketV1();
                        Command command = new Command(CommandEnum.MESSAGE_REQ.getCode(), data);

                        packet.setCommand(command);
                        packet.setSerializerType(SerializerEnum.JSON.getType());

                        ByteBuf dataBuf = ProtocolUtil.encode(packet);
                        // 写数据
                        ByteBuf buffer = channel.alloc().buffer();
                        buffer.writeBytes(dataBuf);
                        // 输出
                        channel.writeAndFlush(buffer);
                    }
                } catch (Exception e) {
                    log.error("console thread fail", e);
                }
            }
        }).start();
     }
}
