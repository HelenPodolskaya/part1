package task1;

import java.util.ArrayList;

/**
 * Задание 1. Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться, но и периодически частично удаляться,
 * чтобы GC имел возможность очищать часть памяти. Через некоторое время программа должна завершиться
 * с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i != 0; i++) {
            list.add(i);
            System.out.println(i);
        }
    }
}

