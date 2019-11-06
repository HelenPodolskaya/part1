package task1;
import java.util.*;

/**
 @author PodolskayaEV
 MyClass - вспомогательный класс, для моделирования ошибок NullPointerException
 ArrayIndexOutOfBoundsException
 */
public class MyClass {
    ArrayList<String> myClassElem;
    public MyClass(ArrayList<String> strElem)
    {this.myClassElem = strElem;}
    public MyClass()
    {}
    public Integer GetCount(){
        return myClassElem.size();// myClassElem == null?null: myClassElem.size();
    }
}

