package task1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива.
 * Использовать пул потоков для решения задачи.
 */
public class Main {
    private static ConcurrentHashMap<Integer, BigInteger> numbersFactorialMap = new ConcurrentHashMap<>();
    private static ArrayList<Integer> numbersList;
    private static List<FutureTask<BigInteger>> futureTaskList = new ArrayList<>();
    private static ExecutorService executor;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        numbersList = generateNumbers(150);
        executor = Executors.newFixedThreadPool(150);
        int currentThreadIndex = 0;
        for (int i = 0; i < numbersList.size(); i++) {
            Integer currentNumber = numbersList.get(i);
            if (!numbersFactorialMap.containsKey(currentNumber)) {
                BigInteger bi = BigInteger.valueOf(1l);
                Integer key = 1;
                if (i != 0) {
                    key = getKey(currentNumber);
                    if (numbersFactorialMap.containsKey(key)) {
                        bi = numbersFactorialMap.get(key);
                    }
                }
                futureTaskList.add(new FutureTask<>(new FactorialRunnable(key, currentNumber, bi)));
                executor.execute(futureTaskList.get(currentThreadIndex));
                getThreadCounter(futureTaskList.get(currentThreadIndex), currentNumber);
                currentThreadIndex++;
            }
        }
        int threadCount = 0;
        while (threadCount < futureTaskList.size()) {
            try {
                threadCount = 0;
                for (int j = 0; j < futureTaskList.size(); j++) {
                    if (getThreadCounter(futureTaskList.get(j), numbersList.get(j))) {
                        threadCount++;
                        System.out.println("Поток: " + j + "число " + numbersList.get(j) + "Факториал: " + numbersFactorialMap.get(numbersList.get(j)));
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    private static boolean getThreadCounter(FutureTask<BigInteger> curFutureTask, Integer key) throws ExecutionException, InterruptedException {
        if (curFutureTask.isDone()) {
            numbersFactorialMap.putIfAbsent(key, curFutureTask.get());
            return true;
        }
        return false;
    }

    private static Integer getKey(Integer numb) {
        Iterator<Integer> iterator = numbersFactorialMap.keySet().iterator();
        Integer key = 1;
        while ((iterator.hasNext())) {
            Integer nextKey = iterator.next();
            if (nextKey <= numb && nextKey > key)
                key = nextKey;
        }
        return key;
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
