package task2;

import java.util.*;

/**
 * @author PodolskayaEV
 */
public class ObjectBox<T> {
    private LinkedHashSet<T> objectsList;

    public LinkedHashSet<T> getObjectsList() {
        return objectsList;
    }

    public ObjectBox(LinkedHashSet<T> objectsList) {
        this.objectsList = objectsList;
    }

    public ObjectBox() {
        this.objectsList = new LinkedHashSet<>();
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

    public String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (T i : objectsList) {
            stringBuilder.append(i.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public void setObjectsList(LinkedHashSet<T> objectsList) {
        this.objectsList = objectsList;
    }

    public boolean addObject(T obj) {
        return objectsList.add(obj);
    }

    public void deleteObject(Object obj) {
        Iterator<T> itr = objectsList.iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(obj)) {
                itr.remove();
                return;
            }
        }
    }
}
