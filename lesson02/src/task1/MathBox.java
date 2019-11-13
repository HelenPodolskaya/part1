package task1;

import task2.ObjectBox;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.UUID;

/**
 * @author PodolskayaEV
 */
public class MathBox extends ObjectBox {
    private ArrayList<Double> numbersList;
    private final String uniqueId = UUID.randomUUID().toString();


    public MathBox(Double[] Number) {
        if (Number.length == 0)
            throw new MathBoxException("Ошибка!Массив не должен быть нулевым!");
        else {
            if (tryEqualsElements(Number) == 0) {
                numbersList = new ArrayList<>();
                for (Double i : Number) {
                    numbersList.add(i);
                }
            } else
                throw new MathBoxException("В массиве есть повторяющиеся элементы! Введите новый массив без повторяющихся элементов.");
        }
    }

    private int tryEqualsElements(Double[] Number) {
        int res = 0;
        for (int i = 0; i < Number.length; i++) {
            for (int j = i + 1; j < Number.length; j++) {
                if (Number[i].equals(Number[j])) {
                    res = 1;
                    return res;
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Double i : numbersList) {
            stringBuilder.append(i);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * @return сумма всех элементов списка
     */
    public Double summator() {
        double summ = 0;
        for (Double i : numbersList) {
            summ += i;
        }
        return summ;
    }

    /**
     * каждый элемент в списке делится на divider
     *
     * @param divider делитель
     */
    public void splitter(int divider) {
        ListIterator<Double> itr = numbersList.listIterator();
        while (itr.hasNext()) {
            itr.set(itr.next() / divider);
        }
    }

    /**
     * найти и удалить элемент в списке
     *
     * @param number элемент который нужно удалить
     */
    public int delete(int number) {
        ListIterator<Double> itr = numbersList.listIterator();
        while (itr.hasNext()) {
            if (itr.next() == (double) number) {
                itr.remove();
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox that = (MathBox) o;
        return Objects.equals(numbersList, that.numbersList);
    }

    @Override
    public int hashCode() {
        return uniqueId.hashCode();
    }
}
