package task1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива.
 * Использовать пул потоков для решения задачи.
 */
public class Main {
    private static ArrayList<Integer> numbersList;
    private static List<Thread> threadsList = new ArrayList<>();
    private static ExecutorService executor;
    private static Scanner in = new Scanner(System.in);
    private static int numbersCount;

    public static void main(String[] args) {
        try {
            System.out.println("Введте количество чисел:");
            numbersCount = in.nextInt();
            StoreMap factorialMap = new StoreMap();
            numbersList = generateNumbers(numbersCount);
            executor = Executors.newFixedThreadPool(numbersCount);
            int currentThreadIndex = 0;
            for (int i = 0; i < numbersList.size(); i++) {
                Integer currentNumber = numbersList.get(i);
                if (!factorialMap.isContainsKey(currentNumber)) {
                    threadsList.add(new Thread(new FactorialRunnable(currentNumber, factorialMap)));
                    executor.execute(threadsList.get(currentThreadIndex));
                    currentThreadIndex++;
                }
            }
            int threadCount = 0;
            while (threadCount < threadsList.size()) {
                threadCount = 0;
                for (int j = 0; j < threadsList.size(); j++) {
                    if (!threadsList.get(j).isAlive()) {
                        threadCount++;
                        System.out.println("Поток: " + j + "число " + numbersList.get(j) + "Факториал: " + factorialMap.get(numbersList.get(j)));
                    }
                }
            }
            executor.shutdown();
            in.close();
        } catch (InputMismatchException ex) {
            System.out.println("Значение должно быть целым числом!");
        }
    }

    private static ArrayList<Integer> generateNumbers(int numbCount) {
        ArrayList<Integer> numbList = new ArrayList<>();
        for (int i = 0; i < numbCount; i++) {
            int k = (int) (1 + Math.random() * numbCount);
            numbList.add(k);
        }
        return numbList;
    }

}
