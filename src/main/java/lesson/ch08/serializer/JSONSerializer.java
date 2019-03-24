package lesson.ch08.serializer;

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
        return new byte[0];
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
