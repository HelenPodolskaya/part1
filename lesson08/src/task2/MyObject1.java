package task2;

public class MyObject1 {
    private int intField;
    private double shortField;
    public String strField;

    public MyObject1(int i_f, double s_f, String str_f) {
        intField = i_f;
        shortField = s_f;
        strField = str_f;
    }

    public MyObject1() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{intField=");
        sb.append(intField);
        sb.append(",shortField=");
        sb.append(shortField);
        sb.append(",strField=");
        sb.append(strField);
        sb.append("}");
        return sb.toString();
    }
}
