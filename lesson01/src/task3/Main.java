package task3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 @author PodolskayaEV
 Задание 3. Дан массив объектов Person. Класс Person характеризуется полями age (возраст, целое число 0-100),
 sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 Создать два класса, методы которых будут реализовывать сортировку объектов.
 Предусмотреть единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:

 первые идут мужчины
 выше в списке тот, кто более старший
 имена сортируются по алфавиту

 Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 Предусмотреть генерацию исходного массива (10000 элементов и более).
 Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.

 */
public class Main {

    public static void main(String[] args) {
        ArrayList<String> manNames = GetManNames();
        ArrayList<String> womanNames = GetWomanNames();
        int exit = 0;// переменная для выхода из программы 0 - не завершать программу, 1 - завершить программу
        while (exit != 1) {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите количество элементов массива:\n");
            try {
                int personCount = in.nextInt();
                ArrayList<Person> personList = GeneratePersonList(manNames, womanNames, personCount);
                System.out.println("Выберите алгоритм сортировки:\n1- пузырьком;\n2-перемешиванием\n");
                int methodSort = in.nextInt();
                if (methodSort != 1 && methodSort != 2)
                    throw new RuntimeException("Неверное число!\n");
                // несовсем точное место для этого, ведь тут ещё примешивается логика выбора класса для сортировки. Пусть и совсем небольшая, но погрешность
                long startTime = System.currentTimeMillis();
                ISorter sorter;
                // на подумать: что если вас попросят добавить ещё один способ сортировки?
                // Можно ли реализовать программу так, чтобы не нужно было добавлять ещё одно условие if или switch-case?
                if (methodSort == 1)
                    sorter = new BubbleSorter();
                else
                    sorter = new CocktailSort();
                sorter.Sort(personList);
                PrintPerson(personList);
                // опять же тут немного вемени уходит ещё и на то, чтобы напечатать
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                // лучше всё таки в миллисикундах
                System.out.println("Время работы алгоритма: " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " секунд.\n");
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
     * Печать массива Person
     * @param personList массив объектов типа Person
     */
    private static void PrintPerson(ArrayList<Person> personList)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < personList.size(); i++) {
            stringBuilder.append(i + 1);
            stringBuilder.append(" возраст = ");
            stringBuilder.append(personList.get(i).getAge());
            stringBuilder.append(" пол = ");
            stringBuilder.append(personList.get(i).getSex().toString());
            stringBuilder.append(" имя = ");
            stringBuilder.append(personList.get(i).getName());
            stringBuilder.append("\n");
        }
        System.out.print(stringBuilder);
    }

    /**
     * Генерация массива объектов типа Person
     * @param manNames массив мужских имён
     * @param womanNames массив женских имён
     * @param n количество элементов массива персон
     * @return массива объектов типа Person
     */
    private static ArrayList<Person> GeneratePersonList(ArrayList<String> manNames,ArrayList<String> womanNames,int n)
    {
        // имена переменных с маленькой буквы
        ArrayList<Person> PersonList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            short age = (short) (Math.random() * (100 + 1));
            Sex_Enum sex;
            String name;
            int k = (int) (Math.random() * (9 + 1));
            int j = (int) (Math.random() * 2);
            if (j == 0) {
                sex = Sex_Enum.MAN;
                name = manNames.get(k);
            } else {
                sex = Sex_Enum.WOMAN;
                name = womanNames.get(k);
            }
            PersonList.add(new Person(age, sex, name));
        }
        return PersonList;
    }

    // javadoc-и это хорошо, но лишь для публичных функций
    /**
     * метод для генерации массива мужских имён
     * @return массив мужских имён
     */
    private static  ArrayList<String> GetManNames()
    {
        ArrayList<String> NamesList = new ArrayList<>();
        NamesList.add("Александр");
        NamesList.add("Евгений");
        NamesList.add("Анатолий");
        NamesList.add("Андрей");
        NamesList.add("Сергей");
        NamesList.add("Владимир");
        NamesList.add("Иван");
        NamesList.add("Михаил");
        NamesList.add("Пётр");
        NamesList.add("Антон");
        return NamesList;
    }

    /**
     * метод для генерации массива женских имён
     * @return массив женских имён
     */
    private static ArrayList<String> GetWomanNames()
    {
        ArrayList<String> NamesList = new ArrayList<>();
        NamesList.add("Александра");
        NamesList.add("Евгения");
        NamesList.add("Антонина");
        NamesList.add("Елена");
        NamesList.add("Наталья");
        NamesList.add("Людмила");
        NamesList.add("Анна");
        NamesList.add("Екатерина");
        NamesList.add("Анастасия");
        NamesList.add("Марина");
        return NamesList;
    }
}

