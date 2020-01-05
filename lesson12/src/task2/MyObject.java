package task2;

public class MyObject implements IMyObjects {
    private String name;

    public String getName() {
        return name;
    }

    public MyObject(String name) {
        this.name = name;
    }

    @Override
    public String SomeMethod() {
        System.out.println("name " + name);
        return name;
    }
}
