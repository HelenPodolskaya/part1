package task1;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * public interface Worker {
 * <p>
 * void doWork();
 * <p>
 * }
 * <p>
 * Необходимо написать программу, выполняющую следующее:
 * <p>
 * Программа с консоли построчно считывает код метода doWork. Код не должен требовать импорта дополнительных классов.
 * После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWork() в файле SomeClass.java.
 * Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * Полученный файл подгружается в программу с помощью кастомного загрузчика
 * Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 */

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Введите путь к файлу SomeClass.java :");
            Path path = Paths.get(in.next());
            ClassWriter writeSomeClass = new ClassWriter();

            /** считываем с консоли код и заносим его в SomeClass.java
             */
            writeSomeClass.readWriteSomeClass(path);

            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            javaCompiler.run(null, null, null, path.toAbsolutePath().toString());

            MyClassLoader classLoader = new MyClassLoader(path.getParent());

            Class<?> loadSomeClass = classLoader.loadClass("task1.SomeClass");
            Worker interfaceWorker = (Worker) loadSomeClass.newInstance();
            interfaceWorker.doWork();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            in.close();
        }
    }
}
