package task1;

import java.util.*;

/**
 * @author PodolskayaEV
 * Задание 1. Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
 * - Смоделировав ошибку NullPointerException
 * - Смоделировав ошибку ArrayIndexOutOfBoundsException
 * - Вызвав свой вариант ошибки через оператор throw
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Hello world!");
            System.out.println("Сгенерировать ошибку: \n1.NullPointerException;\n2.ArrayIndexOutOfBoundsException\n3.Вариант ошибки через оператор throw\nВведите номер:");
            Scanner in = new Scanner(System.in);
            int errorNumber = in.nextInt();
            switch (errorNumber) {
                case 1: {
                    MyClass mc = new MyClass();
                    System.out.println(mc.GetCount());
                }
                break;
                case 2: {
                    MyClass mc = new MyClass(new ArrayList<>());
                    System.out.println(mc.myClassElem.get(0));
                }
                break;
                case 3:
                    throw new RuntimeException("Runtime exception!");
                default:
                    System.out.println("Неверное значение!");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Значение должно быть целым числом!");
        }
    }
}

