package task1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author PodolskayaEV
 * Задание 3. Дан массив объектов Person. Класс Person характеризуется полями age (возраст, целое число 0-100),
 * sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 * Создать два класса, методы которых будут реализовывать сортировку объектов.
 * Предусмотреть единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
 * <p>
 * первые идут мужчины
 * выше в списке тот, кто более старший
 * имена сортируются по алфавиту
 * <p>
 * Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 * Предусмотреть генерацию исходного массива (10000 элементов и более).
 * Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
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
                long startTime = System.currentTimeMillis();
                ISorter sorter;
                switch (methodSort) {
                    case 1:
                        sorter = (listForSort) -> {
                            for (int out = listForSort.size() - 1; out >= 1; out--) {
                                for (int i = 0; i < out; i++) {
                                    int res = listForSort.get(i).getName().compareTo(listForSort.get(i + 1).getName());
                                    if ((listForSort.get(i).getSex() == Sex_Enum.WOMAN && listForSort.get(i + 1).getSex() == Sex_Enum.MAN)
                                            || (listForSort.get(i).getSex() == listForSort.get(i + 1).getSex()
                                            && listForSort.get(i).getAge() < listForSort.get(i + 1).getAge())
                                            || (listForSort.get(i).getSex() == listForSort.get(i + 1).getSex()
                                            && listForSort.get(i).getAge() <= listForSort.get(i + 1).getAge() && (res > 0)))
                                        toSwap(listForSort, i, i + 1);
                                    // а вот если нам нужно провалидировать входные данные, то лучше это сделать сразу
                                    if (res == 0 && listForSort.get(i).getAge() == listForSort.get(i + 1).getAge())
                                        throw new RuntimeException("Имена людей и возраст совпадают " + listForSort.get(i).getName() + " " + listForSort.get(i).getAge());
                                }
                            }
                        };
                        sorter.Sort(personList);
                    case 2:
                        sorter = (listForSort) -> {
                            int left = 0;
                            int right = listForSort.size() - 1;
                            do {
                                for (int i = left; i < right; i++) {
                                    int res = listForSort.get(i).getName().compareTo(listForSort.get(i + 1).getName());
                                    if ((listForSort.get(i).getSex() == Sex_Enum.WOMAN && listForSort.get(i + 1).getSex() == Sex_Enum.MAN)
                                            || (listForSort.get(i).getSex() == listForSort.get(i + 1).getSex()
                                            && listForSort.get(i).getAge() < listForSort.get(i + 1).getAge())
                                            || (listForSort.get(i).getSex() == listForSort.get(i + 1).getSex()
                                            && listForSort.get(i).getAge() <= listForSort.get(i + 1).getAge() && (res > 0)))
                                        toSwap(listForSort, i, i + 1);
                                    if (res == 0 && listForSort.get(i).getAge() == listForSort.get(i + 1).getAge())
                                        throw new RuntimeException("Имена людей и возраст совпадают " + listForSort.get(i).getName() + " " + listForSort.get(i).getAge());
                                }
                                right--;
                                for (int i = right; i > left; i--) {
                                    int res = listForSort.get(i).getName().compareTo(listForSort.get(i - 1).getName());
                                    if ((listForSort.get(i).getSex() == Sex_Enum.MAN && listForSort.get(i - 1).getSex() == Sex_Enum.WOMAN)
                                            || (listForSort.get(i).getSex() == listForSort.get(i - 1).getSex()
                                            && listForSort.get(i).getAge() > listForSort.get(i - 1).getAge())
                                            || (listForSort.get(i).getSex() == listForSort.get(i - 1).getSex()
                                            && listForSort.get(i).getAge() >= listForSort.get(i - 1).getAge() && (res < 0)))
                                        toSwap(listForSort, i, i - 1);
                                    if (res == 0 && listForSort.get(i).getAge() == listForSort.get(i - 1).getAge())
                                        throw new RuntimeException("Имена людей и возраст совпадают " + listForSort.get(i).getName() + " " + listForSort.get(i).getAge());
                                }
                                left++;
                            } while (left < right);
                        };
                        sorter.Sort(personList);
                }
                PrintPerson(personList);
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
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

    private static void toSwap(ArrayList<Person> personList, int first, int second) {
        Person person = personList.get(first);
        personList.set(first, personList.get(second));
        personList.set(second, person);
    }

    /**
     * Печать массива Person
     *
     * @param personList массив объектов типа Person
     */
    private static void PrintPerson(ArrayList<Person> personList) {
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
     *
     * @param manNames   массив мужских имён
     * @param womanNames массив женских имён
     * @param n          количество элементов массива персон
     * @return массива объектов типа Person
     */
    private static ArrayList<Person> GeneratePersonList(ArrayList<String> manNames, ArrayList<String> womanNames, int n) {
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
     *
     * @return массив мужских имён
     */
    private static ArrayList<String> GetManNames() {
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
     *
     * @return массив женских имён
     */
    private static ArrayList<String> GetWomanNames() {
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

