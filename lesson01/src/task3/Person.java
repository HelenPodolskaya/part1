package task3;

/**
 * @author PodolskayaEV
 * Класс Person
 * характеризуется полями age (возраст, целое число 0-100),
 * sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 */
public class Person {
    private short Age;
    private Sex_Enum Sex;
    private String Name;

    /**
     * конструктор
     *
     * @param age  возраст, целое число 0-100
     * @param sex  пол MAN или WOMAN Sex_Enum
     * @param name имя - строка
     */
    public Person(short age, Sex_Enum sex, String name) {
        Age = age;
        Sex = sex;
        Name = name;
    }

    /**
     * @return возвращает имя объекта Person
     */
    public String getName() {
        return Name;
    }


    /**
     * @return short возвращает возраст объекта Person
     */
    public short getAge() {
        return Age;
    }

    /**
     * @return Sex_Enum возвращает пол объекта Person
     */
    public Sex_Enum getSex() {
        return Sex;
    }

}

