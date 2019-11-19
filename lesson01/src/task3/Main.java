package task3;
import task3.Sex_Enum;
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

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (readIsExit() != 1) {
            System.out.println("Введите количество элементов массива:\n");
            try {
                ArrayList<Person> personList = GeneratePersonList(GetManNames(), GetWomanNames(), readPersonCount());
                System.out.println("Выберите алгоритм сортировки:\n1- пузырьком;\n2-перемешиванием\n");
                try {
                    switch (in.nextInt()) {
                        case 1:
                            Sort(personList, new BubbleSorter());
                            break;
                        case 2:
                            Sort(personList, new CocktailSort());
                            break;
                        default:
                            System.out.println("Неверное число!");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Значение должно быть целым числом!");
                    in.next();
                }
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
                in.next();
            }
        }
        in.close();
    }

    private static <T extends ISorter> void Sort(ArrayList<Person> personList, T sortType) {
        long startTime = System.currentTimeMillis();
        sortType.Sort(personList);
        PrintPerson(personList);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Время работы алгоритма: " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " секунд.\n");
    }

    private static int readPersonCount() throws InputMismatchException {
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
        ArrayList<Person> PersonList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
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
            PersonList.add(new Person((short) (Math.random() * (100 + 1)), sex, name));
        }
        return PersonList;
    }

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

