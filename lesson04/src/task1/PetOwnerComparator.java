package task1;

import java.util.Comparator;

public class PetOwnerComparator implements Comparator<Pet> {
    public int compare(Pet a, Pet b) {
        return a.getPetOwner().getName().compareTo(b.getPetOwner().getName());
    }
}