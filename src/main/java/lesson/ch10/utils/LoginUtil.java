package lesson.ch10.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import lesson.ch10.channel.Attributes;
import lesson.ch10.user.UserInfo;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel, UserInfo userInfo) {
        channel.attr(Attributes.LOGIN).set(userInfo);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<UserInfo> userInfo = channel.attr(Attributes.LOGIN);
        return userInfo.get() != null;
    }
}
