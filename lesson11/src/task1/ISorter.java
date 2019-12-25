package task1;

import java.util.ArrayList;

/**
 * @author PodolskayaEV
 * Интерфейс для сортировки массива персон
 */
// в java I в начале не добавляем :)
public interface ISorter {
    /**
     * Метод сортировки массива
     * @param personList массив персон
     */
    // название имён методов с маленькой буквы
    void Sort(ArrayList<Person> personList);
}
