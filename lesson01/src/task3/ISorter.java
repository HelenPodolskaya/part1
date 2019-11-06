package task3;

import java.util.ArrayList;

/**
 * @author PodolskayaEV
 * Интерфейс для сортировки массива персон
 */
public interface ISorter {
    /**
     * Метод сортировки массива
     * @param personList массив персон
     */
    void Sort(ArrayList<Person> personList);
}
