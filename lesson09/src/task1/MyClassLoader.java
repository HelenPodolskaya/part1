package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    private Path filePath;
    public MyClassLoader( Path path) {
        filePath = path;
    }

    /**
     * переопределение loadClass
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("task1.SomeClass")) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * переопределение функции findClass кастомного лоадера
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("вызов переопределенного findClass: " + name);
        if ("task1.SomeClass".equals(name)) {
            try {
                String str = Paths.get(filePath + "\\SomeClass.class").toString();
                byte[] bytes = Files.readAllBytes(Paths.get(str).toAbsolutePath());
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}