package lesson.ch08.serializer;

import java.io.*;

/**
 * title:
 *
 * @author Hao YANG
 * @since 2019.03.27
 */
public class JavaDefaultSerializer implements Serializer {


    @Override
    public byte getSerializerAlgorithm() {
        return SerializerEnum.JAVA_DEFAULT.getType();
    }

    @Override
    public byte[] serialize(Object object) {
       try {
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(bos);
           oos.writeObject(object);
           return bos.toByteArray();
       } catch (Exception e) {
           return null;
       }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            InputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            return (T) obj;
        } catch (Exception e) {
            return null;
        }
    }
}
