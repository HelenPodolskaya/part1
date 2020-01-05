package task1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface IPetLib<Pet> {
    Map<Integer, Pet> getPetHashMap();

    boolean addPet(Pet p) throws PetLibException;

    boolean setPetName(Integer ID, String Name) throws PetLibException;

    boolean setPetWeight(Integer ID, Double Weight) throws PetLibException;

    List<Pet> findPet(String name) throws PetLibException;

    List<Pet> sortPets(Comparator<Pet> pComparator);
}
