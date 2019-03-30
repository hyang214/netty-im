package lesson.ch10.command.handler.impl;

import io.netty.channel.Channel;
import lesson.ch08.command.Command;
import lesson.ch10.command.handler.ResponseCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
@Slf4j
public class MessageResHandler implements ResponseCommandHandler {


    @Override
    public Command handle(Command command, Channel channel) {
        log.info(new Date() + ": client receive message data from server: {} ", command.getData());
        return null;
    }
}
