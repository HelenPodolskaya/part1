package task3;
/**
 * @author PodolskayaEV
 * Класс Person
 *  характеризуется полями age (возраст, целое число 0-100),
 *  sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 */
public class Person {
    private short Age;
    protected enum Sex_Enum{MAN, WOMAN}
    private Sex_Enum Sex;
    private String Name;

    /**
     * конструктор
     * @param age возраст, целое число 0-100
     * @param sex пол MAN или WOMAN Sex_Enum
     * @param name имя - строка
     */
    public Person(short age,Sex_Enum sex,String name)
    {
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

    /**устанавливает
     * @param name имя объекта Person
     */
    public void setName(String name) {
        Name = name;
    }
    /**
     * @return short возвращает возраст объекта Person
     */
    public short getAge() {
        return Age;
    }
    /**устанавливает
     * @param age возраст объекта Person
     */
    public void setAge(short age) {
        Age = age;
    }
    /**
     * @return Sex_Enum возвращает пол объекта Person
     */
    public Sex_Enum getSex() {
        return Sex;
    }
    /**устанавливает
     * @param sex пол объекта Person
     */
    public void setSex(Sex_Enum sex) {
        Sex = sex;
    }
}

