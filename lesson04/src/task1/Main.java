package task1;

import task3.Person;
import task3.Sex_Enum;

import java.util.*;
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
        Pet pet = new Pet(ai.incrementAndGet(), "Пушок", new Person((short) 10, Sex_Enum.MAN, "Вася"), 10.0);
        Pet pet1 = new Pet(ai.incrementAndGet(), "Муся", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 8.0);
        Pet pet2 = new Pet(ai.incrementAndGet(), "Зайчик", new Person((short) 9, Sex_Enum.MAN, "Саша"), 5.0);
        Pet pet3 = new Pet(ai.incrementAndGet(), "Мурка", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 8.0);
        Pet pet4 = new Pet(ai.incrementAndGet(), "Кеша", new Person((short) 11, Sex_Enum.WOMAN, "Маша"), 0.15);
        Pet pet5 = new Pet(ai.incrementAndGet(), "Мурка", new Person((short) 11, Sex_Enum.MAN, "Саша"), 8.0);
        petsLibrary.addPet(pet);
        petsLibrary.addPet(pet1);
        petsLibrary.addPet(pet2);
        petsLibrary.addPet(pet3);
        petsLibrary.addPet(pet4);
        petsLibrary.addPet(pet5);
        while (readIsExit() != 1) {
            System.out.println("Выберите действие:\n1- Добавить животное в картатеку;\n2-Изменить имя животного\n3-Изменить вес животного\n4-Найти животное\n5-Вывести всех животных, отсортированных по имени\n6-Вывести список жиовтных,отсортированных по весу\n7-Вывести список животных\n");
            try {
                switch (in.nextInt()) {
                    case 1:
                        if (addPet()) System.out.println("Животное добавлено!");
                        break;
                    case 2:
                        if (changePetName()) System.out.println("Имя изменено!");
                        else System.out.println("Имя не изменено!");
                        break;
                    case 3:
                        if (changePetWeight()) System.out.println("Вес изменён!");
                        break;
                    case 4:
                        printPets(petsLibrary.findPet(getInPetName("Введите имя животного:")));
                        break;
                    case 5:
                        printPets(petsLibrary.sortPets(new PetNameComparator()));
                        break;
                    case 6:
                        printPets(petsLibrary.sortPets(new PetWeightComparator()));
                        break;
                    case 7:
                        printPets(petsLibrary.getPetHashMap().values());
                        break;
                    default:
                        System.out.println("Неверное число!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Значение должно быть целым числом!");
                in.next();
            } catch (PetLibException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printPets(Collection<Pet> pets) {
        for (Pet p : pets)
            System.out.print(printPetBuilder(p));
    }

    private static String printPetBuilder(Pet pet) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(pet.getPetID());
        stringBuilder.append(" ");
        stringBuilder.append("Имя животного:");
        stringBuilder.append(pet.getPetName());
        stringBuilder.append(" ");
        stringBuilder.append("Вес животного:");
        stringBuilder.append(pet.getPetWeight());
        stringBuilder.append(" ");
        stringBuilder.append("Владелец:");
        stringBuilder.append(pet.getPetOwner().getName());
        stringBuilder.append(" ");
        stringBuilder.append(pet.getPetOwner().getSex());
        stringBuilder.append(" ");
        stringBuilder.append(pet.getPetOwner().getAge());
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    private static String getInOwnerName() throws PetLibException {
        System.out.println("Введите имя хозяина:");
        String ownerName = in.next();
        return ownerName;
    }

    private static Sex_Enum getInOwnerSex() throws PetLibException {
        System.out.println("Введите пол владельца w/m:");
        Sex_Enum ownerSex;
        String sex = in.next();
        if (sex.equals("w")) ownerSex = Sex_Enum.WOMAN;
        else if (sex.equals("m")) ownerSex = Sex_Enum.MAN;
        else throw new PetLibException("Пол владельца введен неверно!");
        return ownerSex;
    }

    private static short getInOwnerAge() throws PetLibException {
        short ownerAge;
        try {
            System.out.println("Введите возраст хозяина:");
            ownerAge = in.nextShort();
        } catch (InputMismatchException ex) {
            throw new PetLibException("Возраст владельца введен неверно!");
        }
        return ownerAge;
    }

    private static String getInPetName(String message) {
        System.out.println(message);
        return in.next();
    }

    private static Double getInPetWeight() throws PetLibException {
        try {
            System.out.println("Введите вес животного:");
            return in.nextDouble();
        } catch (InputMismatchException ex) {
            throw new PetLibException("Некорректно задан вес!");
        }
    }

    private static Pet findPet() throws PetLibException {
        List<Pet> resPets = petsLibrary.findPetByOwner(new Person(getInOwnerAge(), getInOwnerSex(), getInOwnerName()));
        String petName = getInPetName("Введите имя животного:");
        ListIterator<Pet> itr = resPets.listIterator();
        while (itr.hasNext()) {
            Pet pet = itr.next();
            if (pet.getPetName().equals(petName)) {
                return pet;
            }
        }
        throw new PetLibException("Живонтое с таким именем не найдено!");
    }

    private static boolean addPet() throws PetLibException {
        boolean resAdd = false;
        try {
            Person person = new Person(getInOwnerAge(), getInOwnerSex(), getInOwnerName());
            List<Pet> resPets = petsLibrary.findPetByOwner(person);
            String petName = getInPetName("Введите имя животного:");
            ListIterator<Pet> itr = resPets.listIterator();
            while (itr.hasNext()) {
                if (itr.next().getPetName().equals(petName)) {
                    throw new PetLibException("Такое животное уже есть!");
                }
            }
            petsLibrary.addPet(new Pet(ai.incrementAndGet(), petName, person, getInPetWeight()));
            resAdd = true;
        } catch (PetLibException ex) {
            System.out.println(ex.getMessage());
        }
        return resAdd;
    }

    private static boolean changePetName() throws PetLibException {
        boolean resChange = false;
        Pet pet = findPet();
        String newPetName = getInPetName("Введите новое имя животного:");
        resChange = petsLibrary.setPetName(pet.getPetID(), newPetName);
        if (!resChange) System.out.println("Живонтое с таким именем не найдено!");
        return resChange;
    }

    private static boolean changePetWeight() throws PetLibException {
        boolean resChange = false;
        Pet pet = findPet();
        Double newPetWeight = getInPetWeight();
        resChange = petsLibrary.setPetWeight(pet.getPetID(), newPetWeight);
        return resChange;
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
