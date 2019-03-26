package lesson.ch08.serializer;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Getter
public enum SerializerEnum {
    JSON((byte) 1, "JSON", JSONSerializer.class),
    JAVA_DEFAULT((byte) 2, "JavaDefault", JavaDefaultSerializer.class);

    private byte type;
    private String name;
    private Class<? extends Serializer> clazz;

    private static Map<Byte, SerializerEnum> map;

    static {
        map = Maps.newHashMap();
        Arrays.stream(SerializerEnum.values()).forEach((x)->map.put(x.type, x));
    }

    SerializerEnum(byte type, String name, Class<? extends Serializer> clazz) {
        this.type = type;
        this.name = name;
        this.clazz = clazz;
    }

    public static SerializerEnum getByType(byte type) {
        SerializerEnum e = map.get(type);
        if (e == null) {
            throw new IllegalArgumentException("Serializer not found by type: " + type);
        }
        return e;
    }
}
