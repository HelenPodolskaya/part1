package task2;

public class MyObject1 {
    private int intField;
    private short shortField;
    public String strField;

    public MyObject1(int intField, short shortField, String strField) {
        this.intField = intField;
        this.shortField = shortField;
        this.strField = strField;
    }
    public MyObject1(){}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("intField = ");
        sb.append(intField);
        sb.append("]\n");
        sb.append("shortField = ");
        sb.append(shortField);
        sb.append("\n");
        sb.append("strField = ");
        sb.append(strField);
        sb.append("\n");
        return sb.toString();
    }
}
