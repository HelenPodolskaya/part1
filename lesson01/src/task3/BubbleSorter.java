package task3;
import java.util.ArrayList;

/**
 @author PodolskayaEV
 BubbleSorter класс для реализации сортировки методом пузырька
 */
public class BubbleSorter implements ISorter {

    /**
     * @param personList массив объектов Person
     * @param first  и  @param second - элементы массива, которые меняем местами
     */
    private void toSwap(ArrayList<Person> personList, int first, int second) {
        Person person = personList.get(first);
        personList.set(first, personList.get(second));
        personList.set(second, person);
    }

    /**
     * сортировка пузырьком
     * @param personList массив объектов Person
     */
    public void Sort(ArrayList<Person> personList) {
        try {
            for (int out = personList.size() - 1; out >= 1; out--) {
                for (int in = 0; in < out; in++) {
                    int res = personList.get(in).getName().compareTo(personList.get(in + 1).getName());
                    if ((personList.get(in).getSex() == Person.Sex_Enum.WOMAN && personList.get(in + 1).getSex() == Person.Sex_Enum.MAN)
                            || (personList.get(in).getSex() == personList.get(in + 1).getSex()
                            && personList.get(in).getAge() < personList.get(in + 1).getAge())
                            || (personList.get(in).getSex() == personList.get(in + 1).getSex()
                            && personList.get(in).getAge() <= personList.get(in + 1).getAge() && (res > 0)))
                        toSwap(personList, in, in + 1);
                    if(res==0 && personList.get(in).getAge() == personList.get(in + 1).getAge())
                        throw new RuntimeException("Имена людей и возраст совпадают "+personList.get(in).getName()+" "+personList.get(in).getAge());
                }
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

