package task1;
import java.util.Comparator;

public class PetNameComparator implements Comparator<Pet> {
    public int compare(Pet a, Pet b){
        return a.getPetName().compareTo(b.getPetName());
    }
}
