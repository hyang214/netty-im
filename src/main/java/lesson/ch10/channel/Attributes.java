package lesson.ch10.channel;

import io.netty.util.AttributeKey;
import lesson.ch10.user.UserInfo;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.30
 */
public interface Attributes {

    AttributeKey<UserInfo> LOGIN = AttributeKey.newInstance("USER_INFO");
}
