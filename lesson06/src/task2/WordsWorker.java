package task2;
import java.util.ArrayList;

/**
 * класс для генерации текста
 */
public class WordsWorker {
    private static final String literals = "abcdefghijklmnopqrstuvwxyz";
    private static final String sighns = ".?!";

    /**
     * метод, генерирующий слово
     * @param isUp показывает, начинается слово с заглавной буквы или нет
     * @return слово
     */
    public static String generateWord(boolean isUp) {
        int wordLength = (int) (Math.random() * 15 + 1);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int a = (int) (Math.random() * 26);
        if (isUp) {
            sb.append(literals.toUpperCase(), a, a + 1);
            i = 1;
        }
        for (int j = i; j < wordLength; j++) {
            a = (int) (Math.random() * 26);
            sb.append(literals, a, a + 1);
        }
        return sb.toString();
    }

    /**
     * метод, генерирующий масссив слов
     * @return массив строк
     */
    public static ArrayList<String> generateWordsList() {
        ArrayList<String> wordsList = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            wordsList.add(generateWord(false));
        return wordsList;
    }

    /**
     * метод, генерирующий предложение
     * @return текст
     */
    public static String generateSentence() {
        int length = (int) (Math.random() * 15 + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(generateWord(true));
        for (int i = 0; i <= length; i++) {
            sb.append(generateWord(false));
            sb.append(" ");
        }
        length = (int) (Math.random() * 3);
        sb.append(sighns, length, length + 1);
        return sb.toString();
    }

    /**
     * метод, генерирующий текст
     * @param paragraphCount количество абзацев
     * @return сгенерированный текст
     */
    public static String generateText(int paragraphCount) {
        int sentenceCountInParagraph = (int) (Math.random() * 20 + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paragraphCount; i++) {
            sb.append(" \t");
            for (int j = 0; j <= sentenceCountInParagraph; j++)
                sb.append(generateSentence());
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
