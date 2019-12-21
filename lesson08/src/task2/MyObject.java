package task2;

import java.util.ArrayList;
import java.util.Arrays;

public class MyObject {
    private int[] intField;
    private short shortField;
    public String strField;
    public ArrayList<String> arrList;
    public ArrayList<MyObject1> arrListMyObj1;
    private String[] strArray;

    public MyObject(int[] intfield, short shortfield, String strfield) {
        intField = intfield;
        shortField = shortfield;
        strField = strfield;
        strArray = new String[5];
        strArray[0] = String.valueOf(6);
        arrList = new ArrayList<>();

        arrList.add("erere");
        arrListMyObj1 = new ArrayList<>();
        arrListMyObj1.add(new MyObject1(5, (short) 5, "rtrt"));
        //myObj1 = new MyObject1(5,5.6,"object1");
    }

    public MyObject() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("intField = ");
        sb.append(Arrays.toString(intField));
        sb.append("\n");
        sb.append("shortField = ");
        sb.append(shortField);
        sb.append("\n");
        sb.append("strField = ");
        sb.append(strField);
        sb.append("\n");
        sb.append("strArray = ");
        sb.append(Arrays.toString(strArray));
        sb.append("\n");
        sb.append("strArray ={ ");
        for (String s : arrList) {
            sb.append(s);
            sb.append(" ");
        }
        sb.append("}\n");
        sb.append("arrListMyObj1 ={ ");
        for (MyObject1 o : arrListMyObj1) {
            sb.append(o.toString());
            sb.append(" ");
        }
        sb.append("}\n");
        return sb.toString();
    }
}

