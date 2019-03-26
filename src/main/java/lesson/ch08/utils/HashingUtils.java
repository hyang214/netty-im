package lesson.ch08.utils;

import com.google.common.hash.Hashing;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.26
 */
public class HashingUtils {

    public static String sha256(String origin) {
        return Hashing.sha256().hashBytes(origin.getBytes()).toString();
    }
}
