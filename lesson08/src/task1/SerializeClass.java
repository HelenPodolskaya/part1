package task1;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerializeClass {
    private String className;
    private HashMap<String, Object> fieldsHashMap = new HashMap<>();

    /**
     * запись объекта в файл
     *
     * @param object - объект, который помещаем в поток (файл)
     * @param file   - имя файла, в который записывается мой объект
     */
    public void serialize(Object object, Path file, boolean app) throws IllegalAccessException {
        writeFile(file, "", app);
        writeFile(file, "<ClassName>" + object.getClass().getName() + "</ClassName>\r\n", true);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field declaredField : fields) {
            declaredField.setAccessible(true);
            if (declaredField.getType().isPrimitive() || declaredField.getType().getSimpleName().compareTo("String") == 0) {
                writeFile(file, "<FieldName>" + declaredField.getName() + "</FieldName>\r\n", true);
                writeFile(file, "<FieldValue>" + declaredField.get(object) + "</FieldValue>\r\n", true);
            }
        }
    }

    public static void writeFile(Path fileName, String content, boolean append) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(fileName), append)) {
            byte[] buffer = content.getBytes();
            fileOutputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * чтение ранее сериализованных данных из потока
     *
     * @param file - файл, из которого будет читаться поток
     * @return - возвращаем объект
     */
    public Object deSerialize(Path file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        readFile(file);
        Class<?> c = Class.forName(className);
        Object obj = c.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field declaredField : fields) {
            if (fieldsHashMap.containsKey(declaredField.getName())) {
                setField(declaredField.getName(), typeOf(declaredField.getType().getSimpleName(), fieldsHashMap.get(declaredField.getName()).toString()), obj);
            }
        }
        return obj;
    }

    private void readFile(Path file) throws IOException {
        try (FileReader fr = new FileReader(String.valueOf(file));
             Scanner sc = new Scanner(fr)) {
            if (sc.hasNext())
                className = parseLine(sc.nextLine(), "ClassName");
            while (sc.hasNext()) {
                fieldsHashMap.put(parseLine(sc.nextLine(), "FieldName"), parseLine(sc.nextLine(), "FieldValue"));
            }
        }
    }

    private String parseLine(String parseString, String tagName) {
        Pattern pattern = Pattern.compile("<" + tagName + ">(.+?)</" + tagName + ">");
        Matcher m = pattern.matcher(parseString);
        if (m.find())
            return m.group(1);
        else return null;
    }

    private <T> void setField(String fieldName, T fieldValue, @NotNull Object obj) throws IllegalAccessException, NoSuchFieldException {
        Field type = obj.getClass().getDeclaredField(fieldName);
        type.setAccessible(true);
        type.set(obj, fieldValue);
    }

    private Object typeOf(@NotNull String typeName, String value) {
        switch (typeName) {
            case "int":
                return Integer.parseInt(value);
            case "short":
                return Short.parseShort(value);
            case "double":
                return Double.parseDouble(value);
            case "float":
                return Float.parseFloat(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            case "byte":
                return Byte.parseByte(value);
            default:
                return value;
        }
    }
}
