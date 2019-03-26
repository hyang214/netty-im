package lesson.ch08.serializer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
@Slf4j
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerEnum.JSON.getType();
    }

    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = JSONObject.toJSONBytes(object);
//        log.info("bytes: {} {} {}", bytes.length, bytes[0], bytes[bytes.length-1]);
        return bytes;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
//        log.info("bytes: {} {} {}", bytes.length, bytes[0], bytes[bytes.length-1]);
        return JSONObject.parseObject(bytes, clazz);
    }
}
