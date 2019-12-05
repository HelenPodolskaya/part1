package task2;

import java.util.ArrayList;

public class WordLib {
    private ArrayList<String> wordsList;
    private final int wordsCount = 1000;

    public WordLib() {
        generateWordsList();
    }

    public ArrayList<String> getWordsList() {
        return wordsList;
    }

    /**
     * метод, генерирующий масссив слов
     *
     * @return массив строк
     */
    private void generateWordsList() {
        wordsList = new ArrayList<>();
        for (int i = 0; i < wordsCount; i++)
            wordsList.add(WordsWorker.generateWord(false));
    }
}
