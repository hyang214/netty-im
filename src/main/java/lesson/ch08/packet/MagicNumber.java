package lesson.ch08.packet;

import lombok.Data;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Data
public abstract class MagicNumber {

    /**
     * magic number: 用于区分协议
     */
    private final int MAGIC_NUMBER = 0x19910113;
}
