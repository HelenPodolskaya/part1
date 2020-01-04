package task2;

import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * Задание 2.     Доработать программу так, чтобы ошибка OutOfMemoryError возникала
 * в Metaspace /Permanent Generation
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<IMyObjects> list = new ArrayList<>();
        for (int i = 1; i != 0; i++) {
            //Создаем оригинальный объект
            MyObject obj = new MyObject(String.valueOf(i));

            //Получаем загрузчик класса у оригинального объекта
            ClassLoader objClassLoader = obj.getClass().getClassLoader();

            //Получаем все интерфейсы, которые реализует оригинальный объект
            Class[] interfaces = obj.getClass().getInterfaces();

            //Создаем прокси нашего объекта
            IMyObjects proxyMyObj = (IMyObjects) Proxy.newProxyInstance(objClassLoader, interfaces, new MyObjectInvocationHandler(obj));

            //Вызываем у прокси объекта один из методов нашего оригинального объекта
            proxyMyObj.SomeMethod();
            list.add(proxyMyObj);
        }
    }
}
