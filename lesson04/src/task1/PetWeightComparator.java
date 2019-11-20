package task1;

import java.util.Comparator;

public class PetWeightComparator implements Comparator<Pet> {
    public int compare(Pet a, Pet b) {
        return a.getPetWeight().compareTo(b.getPetWeight());
    }
}
