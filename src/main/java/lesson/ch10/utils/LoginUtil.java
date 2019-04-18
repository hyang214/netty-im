package lesson.ch10.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import lesson.ch10.channel.Attributes;
import lesson.ch10.user.UserInfo;

import java.util.Map;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
public class LoginUtil {

    public static Map<String, Channel> userChannelCache = Maps.newHashMap();

    public static void markAsLogin(Channel channel, UserInfo userInfo) {
        channel.attr(Attributes.LOGIN).set(userInfo);
        userChannelCache.put(userInfo.getUsername(), channel);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<UserInfo> userInfo = channel.attr(Attributes.LOGIN);
        return userInfo.get() != null;
    }

    public static void markAsLogout(Channel channel, UserInfo userInfo) {
        channel.attr(Attributes.LOGIN).set(null);
        userChannelCache.remove(userInfo.getUsername());
    }

    public static Channel getTargetChannel(String username) {
        return userChannelCache.get(username);
    }
}
