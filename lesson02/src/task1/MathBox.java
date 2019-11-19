package task1;

import sun.plugin.javascript.navig.Array;
import task2.ObjectBox;

import java.util.*;

/**
 * @author PodolskayaEV
 */
public class MathBox extends ObjectBox<Number> {
    private final String uniqueId = UUID.randomUUID().toString();


    public MathBox(Number[] Numbers) {
        try {
            this.setObjectsList(new LinkedHashSet<>());
            if (Numbers.length == 0)
                throw new MathBoxException("Ошибка!Массив не должен быть нулевым!");
            for (Number i : Numbers)
                if (!addObject(i))
                    throw new MathBoxException("В массиве есть повторяющиеся элементы! Введите новый массив без повторяющихся элементов.");
        } catch (MathBoxException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Number i : this.getObjectsList()) {
            stringBuilder.append(i);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * @return сумма всех элементов списка
     */
    public Number summator() {
        Number summ = 0;
        Iterator<Number> itr = this.getObjectsList().iterator();
        while (itr.hasNext()) {
            summ = sumNumbers(summ,itr.next());
        }
        return summ;
    }
    private Number sumNumbers(Number first, Number second) {
        if (first instanceof Double || second instanceof Double) {
            return first.doubleValue() + second.doubleValue();
        } else if (first instanceof Float || second instanceof Float) {
            return first.floatValue() + second.floatValue();
        } else if (first instanceof Long || second instanceof Long) {
            return first.longValue() + second.longValue();
        } else if (first instanceof Integer || second instanceof Integer) {
            return first.intValue() + second.intValue();
        } else if (first instanceof Short || second instanceof Short) {
            return first.shortValue() + second.shortValue();
        } else {
            return first.byteValue() + second.byteValue();
        }
    }
    /**
     * каждый элемент в списке делится на divider
     *
     * @param divider делитель
     */
    public void splitter(int divider) {
        LinkedHashSet<Number> resObj = new LinkedHashSet<>();
        for (Number nmb : this.getObjectsList()) {
            resObj.add(divNumbers(nmb, divider));
        }
        this.setObjectsList(resObj);
    }

    private Number divNumbers(Number first, Integer second) {
        if (first instanceof Double) {
            return first.doubleValue() / second;
        } else if (first instanceof Float) {
            return first.floatValue() /second;
        } else if (first instanceof Long) {
            return first.longValue() / second;
        } else if (first instanceof Integer) {
            return first.intValue() / second;
        } else if (first instanceof Short) {
            return first.shortValue() / second;
        } else {
            return first.byteValue() / second;
        }
    }

    /**
     * найти и удалить элемент в списке
     *
     * @param number элемент который нужно удалить
     */
    public boolean delete(int number) {
        return this.getObjectsList().removeIf(n -> (n.equals((double)number)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox that = (MathBox) o;
        return Objects.equals(this.getObjectsList(), that.getObjectsList());
    }

    @Override
    public int hashCode() {
        return uniqueId.hashCode();
    }
}
