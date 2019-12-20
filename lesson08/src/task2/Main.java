package task2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException {
        System.out.println("Введите путь к файлу:");
        try {
            Path fileName = Paths.get(in.next());
            SerializeClass sc = new SerializeClass();
            sc.serialize(fileName, new MyObject(new int[]{1}, (short) 2, "serialize"));
            Object obj = sc.deSerialize(fileName);
            MyObject myObject = (MyObject) obj;
            System.out.print(myObject.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (SerializeExceptionClass e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }
}

