package task1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface IPetLib<P> {
    Map<Integer, P> getPetHashMap();

    boolean addPet(P p) throws PetLibException;

    boolean setPetName(Integer ID, String Name) throws PetLibException;

    boolean setPetWeight(Integer ID, Double Weight) throws PetLibException;

    List<P> findPet(String name) throws PetLibException;

    List<P> sortPets(Comparator<P> pComparator);
}
