package task2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * класс для генерации текста
 */
public class WordsWorker {
    private static final String literals = "abcdefghijklmnopqrstuvwxyz";
    private static final String sighns = ".?!";

    /**
     * метод, генерирующий текст
     *
     * @param paragraphCount количество абзацев
     * @return сгенерированный текст
     */
    public static String generateText(int paragraphCount, int probability, ArrayList<String> wordsList) {
        int sentenceCountInParagraph = (int) (Math.random() * 20 + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paragraphCount; i++) {
            sb.append(" \t");
            for (int j = 0; j <= sentenceCountInParagraph; j++)
                sb.append(generateSentence(probability, wordsList));
            sb.append("\r\n");
        }
        return sb.toString();
    }

    /**
     * метод, генерирующий предложение
     *
     * @return текст
     */
    public static String generateSentence(int probability, ArrayList<String> wordsList) {
        int length = (int) (Math.random() * 15 + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(generateWord(true));
        for (int i = 0; i <= length; i++) {
            sb.append(generateWord(false));
            sb.append(" ");
        }
        if (getProbability(probability))
            sb.append(wordsList.get((int) (Math.random() * wordsList.size())));
        length = (int) (Math.random() * 3);
        sb.append(sighns, length, length + 1);
        return sb.toString();
    }

    public static boolean getProbability(int probability) {
        return ThreadLocalRandom.current().nextInt(10) > probability ? true : false;
    }

    /**
     * метод, генерирующий слово
     *
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
}
