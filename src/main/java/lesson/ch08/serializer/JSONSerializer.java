package lesson.ch08.serializer;

import com.alibaba.fastjson.JSONObject;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.24
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerEnum.JSON.getType();
    }

    @Override
    public byte[] serialize(Object object) {
        return JSONObject.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes, clazz);
    }
}
