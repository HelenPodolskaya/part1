package task2;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerializeClass {
    private Path file;

    /**
     * запись объекта в файл
     *
     * @param object - объект, который помещаем в поток (файл)
     */


    public void serialize(Path fileName, Object object) throws IllegalAccessException {
        file = fileName;
        writeFile("");
        writeFile(generateSerializeText(object));
    }

    public String generateSerializeText(Object object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        //Записывем имя класса
        sb.append(generateStringWithTags(TagsEnum.ClassName, object.getClass().getName()));
        //возвращаем поля
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field declaredField : fields) {
            declaredField.setAccessible(true);
            sb.append(generateStringWithTags(TagsEnum.FieldName, declaredField.getName()));
            //Если поле примитивного типа или String
            if (declaredField.getType().isPrimitive() || declaredField.getType().getSimpleName().equals("String")) {
                sb.append(generateStringWithTags(TagsEnum.FieldValue, declaredField.get(object).toString()));
            } else {
                String str = declaredField.get(object).getClass().getName();
                sb.append(generateStringWithTags(TagsEnum.ClassName, declaredField.get(object).getClass().getName()));
                //если массив
                if (declaredField.getType().getSimpleName().matches("\\w*\\[\\]$")) {
                    sb.append(typeOfArray(declaredField.getType().getSimpleName(), declaredField.get(object)));
                } else {
                    //if List
                    if (declaredField.getType().getSimpleName().matches("\\w*List\\w*$")) {
                        sb.append(generateCollectionSerializeText((List<Object>) declaredField.get(object)));
                    } else
                        //if Set
                        if (declaredField.getType().getSimpleName().matches("\\w*Set\\w*$")) {
                            sb.append(generateCollectionSerializeText((Set<Object>) declaredField.get(object)));
                        } else if (declaredField.getType().getSimpleName().matches("\\w*Queue\\w*$")) {
                            sb.append(generateCollectionSerializeText((Queue<Object>) declaredField.get(object)));
                        } else if (declaredField.getType().getSimpleName().matches("\\w*Map\\w*$")) {
                            Map<Object, Object> al = (Map<Object, Object>) declaredField.get(object);
                            sb.append(generateStringWithTags(TagsEnum.FieldSize, al.size()));
                            for (Map.Entry<Object, Object> entry : al.entrySet()) {
                                if (entry.getValue().getClass().isPrimitive() || entry.getValue().getClass().getSimpleName().compareTo("String") == 0) {
                                    sb.append(generateStringWithTags(TagsEnum.Key, entry.getKey()));
                                    sb.append(generateStringWithTags(TagsEnum.Value, entry.getValue()));
                                } else {
                                    sb.append(generateStringWithTags(TagsEnum.Key, entry.getKey()));
                                    sb.append(generateSerializeText(entry.getValue()));
                                }
                            }
                        } else
                            sb.append(generateSerializeText(declaredField.get(object)));
                }
            }
        }
        return sb.toString();
    }

    // генерация строки с тегом для записи в файл
    public String generateStringWithTags(TagsEnum tag, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(tag.toString());
        sb.append(">");
        sb.append(Objects.nonNull(value) ? value.toString() : "null");
        sb.append("</");
        sb.append(tag.toString());
        sb.append(">\r\n");
        return sb.toString();
    }
    // генерация строки с тегом для записи  колеекции в файл
    private String generateCollectionSerializeText(Collection<?> collection) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(generateStringWithTags(TagsEnum.FieldSize, collection.size()));
        for (Object o : collection) {
            if (o.getClass().isPrimitive() || o.getClass().getSimpleName().equals("String"))
                sb.append(generateStringWithTags(TagsEnum.FieldElem, o));
            else
                sb.append(generateSerializeText(o));
        }
        return sb.toString();
    }

    private void writeFile(String content) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(file), false)) {
            byte[] buffer = content.getBytes();
            fileOutputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * чтение ранее сериализованных данных из потока
     *
     * @param fileName - файл, из которого будет читаться поток
     * @return - возвращаем объект
     */
    public Object deSerialize(Path fileName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        file = fileName;
        String className = readClassName(file);
        if (className.compareTo("") != 0) {
            return instantiateObjectOfClass(className);
        } else
            throw new SerializeExceptionClass("Ошибка при десериализации класса.");
    }

    private String readClassName(Path file) throws IOException {
        try (FileReader fr = new FileReader(String.valueOf(file));
             Scanner sc = new Scanner(fr)) {
            if (sc.hasNext())
                return parseLine(sc.nextLine(), "ClassName");
            else return "";
        }
    }

    private void findClassField(Field field, Object obj, String className) throws IOException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try (FileReader fr = new FileReader(String.valueOf(file));
             Scanner sc = new Scanner(fr)) {
            while (sc.hasNextLine()) {
                if (className.compareTo(parseLine(sc.nextLine(), "ClassName")) == 0) {
                    while (sc.hasNextLine()) {
                        if (field.getName().compareTo(parseLine(sc.nextLine(), "FieldName")) == 0) {
                            if ((field.getType().isPrimitive() || field.getType().getSimpleName().equals("String")) && sc.hasNextLine()) {
                                setField(field.getName(), typeOf(field.getType().getSimpleName(), parseLine(sc.nextLine(), "FieldValue")), obj);
                                break;
                            } else if ((!field.getType().isPrimitive() && sc.hasNextLine()) &&
                                    field.getType().getSimpleName().matches("\\w*\\[\\]$")) {
                                setSimpleArray(sc, field, obj);
                                break;
                            } else if ((!field.getType().isPrimitive() && sc.hasNextLine()) &&
                                    (field.getType().getSimpleName().matches("\\w*List\\w*$") ||
                                            field.getType().getSimpleName().matches("\\w*Set\\w*$") ||
                                            field.getType().getSimpleName().matches("\\w*Queue\\w*$"))) {
                                setCollection(sc, false, obj, field);
                                break;
                            }
                            if ((!field.getType().isPrimitive() && sc.hasNextLine()) &&
                                    field.getType().getSimpleName().matches("\\w*Map\\w*$")) {
                                setCollection(sc, true, obj, field);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void setCollection(Scanner sc, boolean isMap, Object obj, Field field) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        String className = parseLine(sc.nextLine(), "ClassName");
        Class<?> currentClassName = Class.forName(className);
        int sizeSimpleArray = Integer.parseInt(parseLine(sc.nextLine(), "FieldSize"));
        String str = sc.nextLine();
        if (parseLine(str, "ClassName").compareTo("") == 0 && !isMap)
            pushCollection(currentClassName, sizeSimpleArray, obj, field, "add", new Class[]{Object.class}, new Object[]{typeOf(field.getType().getSimpleName(), parseLine(str, "FieldElem"))});
        if (parseLine(str, "ClassName").compareTo("") != 0 && !isMap)
            pushCollection(currentClassName, sizeSimpleArray, obj, field, "add", new Class[]{Object.class}, new Object[]{instantiateObjectOfClass(parseLine(str, "ClassName"))});
        if (isMap) {
            Object key;
            if (parseLine(str, "ClassName").compareTo("") == 0)
                key = typeOf(field.getType().getSimpleName(), parseLine(str, "Key"));
            else key = instantiateObjectOfClass(str);
            String str1 = sc.nextLine();
            if (parseLine(str1, "ClassName").compareTo("") == 0)
                pushCollection(currentClassName, sizeSimpleArray, obj, field, "put", new Class[]{Object.class, Object.class}, new Object[]{key, typeOf(field.getType().getSimpleName(), parseLine(str1, "Value"))});
            else {
                pushCollection(currentClassName, sizeSimpleArray, obj, field, "put", new Class[]{Object.class, Object.class}, new Object[]{key, instantiateObjectOfClass(parseLine(str1, "ClassName"))});

            }
        }
    }

    private Object instantiateObjectOfClass(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IOException {
        Class<?> c = Class.forName(className);
        Object obj = c.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field declaredField : fields)
            findClassField(declaredField, obj, className);
        return obj;
    }

    private void pushCollection(Class<?> c, int sizeSimpleArray, Object obj, Field field, String
            methodName, Class<?>[] params, Object[] args) throws
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object o = c.newInstance();
        Method m = c.getMethod(methodName, params);
        for (int i = 0; i < sizeSimpleArray; i++) {
            m.invoke(o, args);
            setField(field.getName(), o, obj);
        }
    }

    private String parseLine(String parseString, String tagName) {
        Pattern pattern = Pattern.compile("<" + tagName + ">(.+?)</" + tagName + ">");
        Matcher m = pattern.matcher(parseString);
        if (m.find())
            return m.group(1);
        else return "";
    }

    private <T> void setField(String fieldName, T fieldValue, @NotNull Object obj) throws
            IllegalAccessException, NoSuchFieldException {
        Field type = obj.getClass().getDeclaredField(fieldName);
        type.setAccessible(true);
        type.set(obj, fieldValue);
    }

    private String typeOfArray(String typeName, Object obj) {
        StringBuilder sb = new StringBuilder();
        switch (typeName) {
            case "int[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((int[]) obj).length));
                for (int i = 0; i < ((int[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((int[]) obj)[i]));
                break;
            case "long[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((long[]) obj).length));
                for (int i = 0; i < ((long[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((long[]) obj)[i]));
                break;
            case "float[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((float[]) obj).length));
                for (int i = 0; i < ((float[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((float[]) obj)[i]));
                break;
            case "double[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((double[]) obj).length));
                for (int i = 0; i < ((double[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((double[]) obj)[i]));
                break;
            case "char[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((char[]) obj).length));
                for (int i = 0; i < ((char[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((char[]) obj)[i]));
                break;
            case "String[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((String[]) obj).length));
                for (int i = 0; i < ((String[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((String[]) obj)[i]));
                break;
            case "byte[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((byte[]) obj).length));
                for (int i = 0; i < ((byte[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((byte[]) obj)[i]));
                break;
            case "boolean[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((boolean[]) obj).length));
                for (int i = 0; i < ((boolean[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((boolean[]) obj)[i]));
                break;
            case "short[]":
                sb.append(generateStringWithTags(TagsEnum.FieldSize, ((short[]) obj).length));
                for (int i = 0; i < ((short[]) obj).length; i++)
                    sb.append(generateStringWithTags(TagsEnum.FieldElem, ((short[]) obj)[i]));
                break;
        }
        return sb.toString();
    }

    private void setSimpleArray(Scanner sc, Field field, Object obj) throws
            NoSuchFieldException, IllegalAccessException {
        parseLine(sc.nextLine(), "ClassName");
        int sizeSimpleArray = Integer.parseInt(parseLine(sc.nextLine(), "FieldSize"));
        switch (field.getType().getSimpleName()) {
            case "int[]":
                int[] o = new int[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    o[i] = Integer.parseInt(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), o, obj);
                break;
            case "short[]":
                short[] s = new short[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    s[i] = Short.parseShort(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), s, obj);
                break;
            case "double[]":
                double[] d = new double[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    d[i] = Double.parseDouble(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), d, obj);
                break;
            case "float[]":
                float[] f = new float[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    f[i] = Float.parseFloat(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), f, obj);
                break;
            case "boolean[]":
                boolean[] b = new boolean[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    b[i] = Boolean.parseBoolean(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), b, obj);
                break;
            case "byte[]":
                byte[] bt = new byte[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    bt[i] = Byte.parseByte(parseLine(sc.nextLine(), "FieldElem"));
                setField(field.getName(), bt, obj);
                break;
            case "char[]":
                char[] c = new char[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    c[i] = parseLine(sc.nextLine(), "FieldElem").charAt(0);
                setField(field.getName(), c, obj);
                break;
            case "String[]":
                String[] str = new String[sizeSimpleArray];
                for (int i = 0; (i < sizeSimpleArray && sc.hasNextLine()); i++)
                    str[i] = parseLine(sc.nextLine(), "FieldElem");
                setField(field.getName(), str, obj);
                break;
        }
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
