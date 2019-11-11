package task2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author PodolskayaEV
 * Задание 2. Составить программу, генерирующую N случайных чисел.
 * Для каждого числа k вычислить квадратный корень q.
 * Если квадрат целой части q числа равен k, то вывести это число на экран.
 * Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */
public class Main {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (readIsExit() != 1) {
            System.out.println("Введите количество чисел:");
            try {
                int arrayCount = readArrayCount();
                ArrayList<Integer> numbersArray = new ArrayList<>();
                for (int i = 0; i < arrayCount; i++) {
                    int k = (int) (Math.random() * (arrayCount + arrayCount + 1)) - arrayCount; // генерация числа k
                    try {
                        if (Math.pow((int) Math.sqrt(k), 2) == k)
                            numbersArray.add(k);
                        if (k < 0)
                            throw new RuntimeException("Число отрицательное! " + k);
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                printNumbersArray(numbersArray, arrayCount);
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
            }
        }
        in.close();
    }

    private static int readArrayCount() throws InputMismatchException {
        return in.nextInt();
    }

    private static int readIsExit() {
        System.out.println("\nЗавершить программу? y/n");
        int exit = 0;// переменная для выхода из программы 0 - не завершать программу, 1 - завершить программу
        String str_exit = in.next();
        if (str_exit.equals("y"))
            exit = 1;
        return exit;
    }

    /**
     * Функция печати элементов массива
     *
     * @param numbersArray
     */
    public static void printNumbersArray(ArrayList<Integer> numbersArray, int n) {
        System.out.print("Из " + n + " сгенерированных чисел квадрат целой части корня числа равен исходному числу в " + numbersArray.size() + " случаях:\n");
        for (int i : numbersArray) {
            System.out.print(i + " ");
        }
    }
}

