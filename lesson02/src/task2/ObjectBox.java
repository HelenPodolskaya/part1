package task2;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

/**
 * @author PodolskayaEV
 */
public class ObjectBox {
    private ArrayList<Object> objectsList;

    public ObjectBox(ArrayList<Object> objectsList) {
        this.objectsList = objectsList;
    }

    public ObjectBox() {
        this.objectsList = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ObjectBox that = (ObjectBox) obj;
        return Objects.equals(objectsList, that.objectsList);
    }

    private String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Object i : objectsList) {
            stringBuilder.append(i.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private void addObject(Object obj) {
        objectsList.add(obj);
    }

    private void deleteObject(Object obj) {
        ListIterator<Object> itr = objectsList.listIterator();
        while (itr.hasNext()) {
            if (itr.next().equals(obj)) {
                itr.remove();
                return;
            }
        }
    }
}
