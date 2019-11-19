package task1;

import task3.Person;
import task3.Sex_Enum;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author PodolskayaEV
 * Разработать программу – картотеку домашних животных.
 */
public class Main {
    private static Scanner in = new Scanner(System.in);
    private static PetLib petsLibrary = new PetLib();
    private static AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) {
/*
        while (readIsExit() != 1) {
            System.out.println("Выберите действие:\n1- Добавить животное в кавртатеку;\n2-Изменить имя животного\n3-Изменить вес животного\n4-Найти животное\n5-Вывести всех животного, отсортированных по имени\n");
            try {
                switch (in.nextInt()) {
                    case 1:
                       addPet();
                        break;
                    case 2:

                        break;
                    default:
                        System.out.println("Неверное число!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
                in.next();
            }
        }*/

        Pet pet = new Pet(ai.incrementAndGet(), "Пушок", new Person((short) 10, Sex_Enum.MAN, "Вася"), 10.0);
        Pet pet1 = new Pet(ai.incrementAndGet(), "Муся", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 8.0);
        Pet pet2 = new Pet(ai.incrementAndGet(), "Зайчик", new Person((short) 9, Sex_Enum.MAN, "Саша"), 5.0);
        Pet pet3 = new Pet(ai.incrementAndGet(), "Мурка", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 8.0);
        Pet pet4 = new Pet(ai.incrementAndGet(), "Кеша", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 0.15);
        PetLib pl = new PetLib();
        pl.addPet(pet);
        pl.addPet(pet1);
        pl.addPet(pet2);
        pl.addPet(pet3);
        pl.addPet(pet4);
        pl.setPetName(2, "Мурка");
        List<Pet> pets = pl.findPetByOwner(new Person((short) 11, Sex_Enum.WOMAN, "Маша"));
        for (Pet p : pets)
            System.out.println(p.getPetID() + " " + p.getPetName());
        pets = pl.findPet("Мурка");
        for (Pet p : pets)
            System.out.println(p.getPetID() + " " + p.getPetName());
        pets.clear();
        pets = pl.sortPets(new PetNameComparator());
        for (Pet p : pets)
            System.out.println(p.getPetID() + " " + p.getPetName());
        pets.clear();
        pets = pl.sortPets(new PetWeightComparator());
        for (Pet p : pets)
            System.out.println(p.getPetID() + " " + p.getPetName() + " " + p.getPetWeight());
        addPet();
    }

    private static Sex_Enum getInOwnerSex() throws PetLibException {
        System.out.println("Введите пол:");
        String sex = in.next();
        Sex_Enum ownerSex;
        if (sex == "w") ownerSex = Sex_Enum.WOMAN;
        else if (sex == "m") ownerSex = Sex_Enum.MAN;
        else throw new PetLibException("Пол владельца введен неверно!");
        return ownerSex;
    }

    private static short getInOwnerAge() throws PetLibException {
        short ownerAge;
        try {
            System.out.println("Введите возраст:");
            ownerAge = in.nextShort();
        } catch (InputMismatchException ex) {
            throw new PetLibException("Возраст владельца введен неверно!");
        }
        return ownerAge;
    }

    private static void addPet() {
        try {
            System.out.println("Введите имя хозяина:");
            String ownerName = in.next();
            System.out.println("Введите имя животного:");
            String petName = in.next();
            System.out.println("Введите вес животного:");
            Double petWeight = in.nextDouble();
            petsLibrary.addPet(new Pet(ai.incrementAndGet(), petName, (new Person(getInOwnerAge(), getInOwnerSex(), ownerName)), petWeight));
        } catch (PetLibException ex) {
            System.out.println(ex.getMessage());
        }

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
