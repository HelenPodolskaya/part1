package task2;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 @author PodolskayaEV
 Задание 2. Составить программу, генерирующую N случайных чисел.
 Для каждого числа k вычислить квадратный корень q.
 Если квадрат целой части q числа равен k, то вывести это число на экран.
 Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */
public class Main {

    public static void main(String[] args) {
        int arrayCount = 0; //количесвто чисел в массиве(вводится с консоли)
        int exit = 0;// переменная для выхода из программы 0 - не завершать программу, 1 - завершить программу
        ArrayList<Integer> numbersArray;// массив чисел q, для которых квадрат целой части q равен k
        while (exit != 1) {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите количество чисел:");
            try {
                arrayCount = in.nextInt();
                numbersArray = new ArrayList<>();
                for (int i = 0; i < arrayCount; i++) {
                    int k = (int) (Math.random() * (arrayCount + arrayCount + 1)) - arrayCount; // генерация числа k
                    try {
                        if (k < 0) {
                            i--;
                            throw new RuntimeException("Число отрицательное! " + k);
                        } else {
                            double q = Math.sqrt(k);
                            if (Math.pow((int) q, 2) == k) {
                                numbersArray.add(k);
                            }
                        }
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                printNumbersArray(numbersArray, arrayCount);
                System.out.println("\nЗавершить программу? y/n");
                String str_exit = in.next();
                if (str_exit.equals("y")) {
                    exit = 1;
                    in.close();
                }
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            } finally {

            }
        }
    }

    /**
     Функция печати элементов массива
     * @param numbersArray
     */
    public static void printNumbersArray(ArrayList<Integer> numbersArray, int n) {
        System.out.print("Из "+n+" сгенерированных положительных чисел квадрат целой части корня числа равен исходному числу в "+ numbersArray.size()+" случаях:\n");
        for (int i : numbersArray) {
            System.out.print(i + " ");
        }
    }
}


