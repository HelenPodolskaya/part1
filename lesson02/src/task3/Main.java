package task3;

import task1.MathBox;
import task1.MathBoxException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author PodolskayaEV
 * Задание 1. Написать класс MathBox, реализующий следующий функционал:
 * Конструктор на вход получает массив Number. Элементы не могут повторяться.
 * Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
 * Существует метод summator, возвращающий сумму всех элементов коллекции.
 * Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
 * Хранящиеся в объекте данные полностью заменяются результатами деления.
 * Необходимо правильно переопределить методы toString, hashCode, equals, чтобы можно было использовать MathBox для вывода данных на экран и
 * хранение объектов этого класса в коллекциях (например, hashMap). Выполнение контракта обязательно!
 * Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.
 */
public class Main {
    private static Scanner in = new Scanner(System.in);
    private static MathBox mb;

    public static void main(String[] args) {
        mb = createNewList();
        while (readIsExit() != 1) {
            try {
                System.out.println("Выберите действие:\n0-Созать новый массив;\n1-Посчитать сумму элементов;\n2-Разделить все элементы на число;\n3-Найти элемент и удалить\n");
                switch (in.nextInt()) {
                    case 0:
                        mb = createNewList();
                        break;
                    case 1:
                        System.out.println("сумма элементов = " + mb.summator());
                        break;
                    case 2:
                        System.out.println("введите делитель:");
                        mb.splitter(in.nextInt());
                        System.out.println(mb.toString());
                        break;
                    case 3:
                        System.out.println("введите число:");
                        if (mb.delete(in.nextInt()) == 0)
                            System.out.println("Такого чистла нет в списке элементов!");
                        System.out.println(mb.toString());
                        break;
                    default:
                        System.out.println("Неверное число!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
                in.next();
            } catch (MathBoxException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static MathBox createNewList() {
        System.out.println("Введите количество чисел в массиве:");
        MathBox mb = new MathBox(inNumbers(readNumbersCount()));
        return mb;
    }

    private static Double[] inNumbers(int countNumb) {
        Double[] Numbers = new Double[countNumb];
        for (int i = 0; i < countNumb; i++) {
            try {
                System.out.println("Введите элемент массива № " + i);
                Numbers[i] = in.nextDouble();
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть числом!");
                in.next();
                i--;
            }
        }
        return Numbers;
    }

    private static int readNumbersCount() throws InputMismatchException {
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
}
