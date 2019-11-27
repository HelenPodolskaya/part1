package task2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Создать генератор текстовых файлов, работающий по следующим правилам:
 * <p>
 * Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение
 * (1/probability).
 * <p>
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
 * который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 */
public class Main {
    public static void main(String[] args) throws IOException {
       getFiles("C:\\temp", 3, 40000, WordsWorker.generateWordsList(), 3);
    }

    /**
     * добавить запись в файл
     *
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, String content) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName, true)) {
            byte[] buffer = content.getBytes();
            fileOutputStream.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод, создающий файлы
     *
     * @param path        каталог
     * @param n           файлов
     * @param size        размер файлов
     * @param words       массив слов
     * @param probability вероятность попадания слова
     * @throws IOException
     */
    public static void getFiles(String path, int n, int size, ArrayList<String> words, int probability) throws IOException {
        String fileName = "note";
        Path dirpath = Paths.get(path);
        if (!Files.isDirectory(dirpath)) {
            Files.createDirectory(dirpath);
        }
        for (int j = 0; j < n; j++) {
            Path filePath = Paths.get(dirpath.toString(), fileName + j + ".txt");
            if (Files.notExists(filePath)) Files.createFile(filePath);
            while (Files.size(filePath) < size) {
                writeFile(filePath.toString(), WordsWorker.generateText(1, probability,words));
            }
        }
    }
}
