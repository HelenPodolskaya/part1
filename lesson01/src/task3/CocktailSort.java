package task3;
import java.util.ArrayList;
/**
 @author PodolskayaEV
 CocktailSort класс для реализации сортировки перемешиванием
 */
public class CocktailSort implements ISorter {
    /**
     * @param personList массив объектов Person
     * @param first      и  @param second - элементы массива, которые меняем местами
     */
    private void toSwap(ArrayList<Person> personList, int first, int second) {
        Person person = personList.get(first);
        personList.set(first, personList.get(second));
        personList.set(second, person);
    }
    /**
     * сортировка перемешиванием
     * @param personList массив объектов Person
     */
    public void Sort(ArrayList<Person> personList) {
        int left = 0;
        int right = personList.size() - 1;
        try {
            do {
                // куски кода блоках циклов очень похожи, чтобы соблюсти принцип DRY можно вынести их в функцию
                for (int i = left; i < right; i++) {
                    int res = personList.get(i).getName().compareTo(personList.get(i + 1).getName());
                    if ((personList.get(i).getSex() == Sex_Enum.WOMAN && personList.get(i + 1).getSex() ==Sex_Enum.MAN)
                            || (personList.get(i).getSex() == personList.get(i + 1).getSex()
                            && personList.get(i).getAge() < personList.get(i + 1).getAge())
                            || (personList.get(i).getSex() == personList.get(i + 1).getSex()
                            && personList.get(i).getAge() <= personList.get(i + 1).getAge() && (res > 0)))
                        toSwap(personList, i, i + 1);
                    if (res == 0 && personList.get(i).getAge() == personList.get(i + 1).getAge())
                        throw new RuntimeException("Имена людей и возраст совпадают " + personList.get(i).getName() + " " + personList.get(i).getAge());
                }
                right--;
                for (int i = right; i > left; i--) {
                    int res = personList.get(i).getName().compareTo(personList.get(i - 1).getName());
                    if ((personList.get(i).getSex() == Sex_Enum.MAN && personList.get(i - 1).getSex() == Sex_Enum.WOMAN)
                            || (personList.get(i).getSex() == personList.get(i - 1).getSex()
                            && personList.get(i).getAge() > personList.get(i - 1).getAge())
                            || (personList.get(i).getSex() == personList.get(i - 1).getSex()
                            && personList.get(i).getAge() >= personList.get(i - 1).getAge() && (res < 0)))
                        toSwap(personList, i, i - 1);
                    if (res == 0 && personList.get(i).getAge() == personList.get(i - 1).getAge())
                        throw new RuntimeException("Имена людей и возраст совпадают " + personList.get(i).getName() + " " + personList.get(i).getAge());
                 }
                left++;
            } while (left < right);
        }
        // подходящее ли это место? И точно ли RuntimeException всегда будет значить, что это про совпадение имён и возрастовлюдей?
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
