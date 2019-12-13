package task1;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;


public class ClassWriter {
    /**
     * класс для создания файла SomeClass
     * содержит методы для считывания с консоли пользовательских строк
     * и записи их в файл
     */
    public ClassWriter() {
    }

    /**
     * функция считывания с клавиатуры текста метода doWork()
     * и занесения в файл
     *
     * @param path - путь до файла
     */
    public void readWriteSomeClass(Path path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            StringBuilder sb = new StringBuilder();
            sb.append("package task1;\r\n");
            sb.append("public class SomeClass implements task1.Worker {\r\n");
            sb.append("\r\n");
            sb.append("@Override\r\n");
            sb.append("   public void doWork() {\r\n");
            System.out.println("Введите код метода doWork:");
            String resCode;
            while (!(resCode = bufferedReader.readLine()).equals("")) {
                sb.append(resCode);
                sb.append("\r\n");
            }
            sb.append("}\r\n}");
            writeFile(path, sb.toString(), false);
        }
    }

    public void writeFile(Path fileName, String content, boolean append) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(fileName), append)) {
            byte[] buffer = content.getBytes();
            fileOutputStream.write(buffer);
        }
    }
}


