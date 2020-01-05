package task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyObjectInvocationHandler implements InvocationHandler {

    private MyObject myObject;

    public MyObjectInvocationHandler( MyObject myObject) {

        this.myObject = myObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Привет!");
        return method.invoke(myObject, args);
    }
}
