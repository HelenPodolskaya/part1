package task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Написать программу, читающую текстовый файл.
 * Программа должна составлять отсортированный по алфавиту список слов, найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 */
public class Main {
    private static List<String> stringList = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Введите путь к файлу:");
            Path readFilePath = Paths.get( in.next());
            if (Files.exists(readFilePath)) {
                Path writeFilePath = Paths.get("C:\\temp\\note1.txt");
                List<String> sortedStringList = Sort(readFile(readFilePath));
                Files.write(writeFilePath, sortedStringList, StandardCharsets.UTF_8);
                for (String s : sortedStringList)
                    System.out.println(s);
            } else System.out.println("Заданный файл не существует!");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<String> readFile(Path path) {
        List<String> readStringList = new ArrayList<>();
        try {
            stringList = Files.readAllLines(path);
            for (String s : stringList) {
                String[] str = s.split("[\\p{Punct}\\s]+");
                for (String s1 : str) {
                    int i = 0;
                    for (String s2 : readStringList) {
                        int found1 = s1.toLowerCase().compareTo(s2.toLowerCase());
                        if (found1 == 0) {
                            i = 1;
                            break;
                        }
                    }
                    if (readStringList.size() == 0 || i == 0)
                        readStringList.add(s1);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return readStringList;
    }

    private static List<String> Sort(List<String> wordList) {
        for (int out = wordList.size() - 1; out >= 1; out--) {
            for (int in = 0; in < out; in++) {
                int res = wordList.get(in).toLowerCase().compareTo(wordList.get(in + 1).toLowerCase());
                if (res > 0)
                    toSwap(wordList, in, in + 1);
            }
        }
        return wordList;
    }

    private static void toSwap(List<String> wordList, int first, int second) {
        String word = wordList.get(first);
        wordList.set(first, wordList.get(second));
        wordList.set(second, word);
    }
}
