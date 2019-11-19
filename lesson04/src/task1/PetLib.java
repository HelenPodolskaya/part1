package task1;
import task3.Person;

import java.util.*;
import java.util.stream.Collectors;

public class PetLib {
    private HashMap<Integer, Pet> petHashMap;

    public HashMap<Integer, Pet> getPetHashMap() {
        return petHashMap;
    }

    public PetLib() {
        petHashMap = new HashMap<>();
    }

    public void addPet(Pet pet) {
        petHashMap.put(pet.getPetID(), pet);
    }

    public void setPetName(Integer petID, String petName) {
        if (petHashMap.containsKey(petID)) {
            Pet pet = petHashMap.get(petID);
            pet.setPetName(petName);
        }
    }

    public void setPetWeight(Integer petID, Double petWeight) {
        Pet pet = petHashMap.get(petID);
        pet.setPetWeight(petWeight);
    }

    public List<Pet> findPet(String petName) {
        List<Pet> result = petHashMap.values().stream()
                .filter(Pet -> Objects.equals(Pet.getPetName(), petName))
                .collect(Collectors.toList());
        return result;
    }

    public List<Pet> findPetByOwner(Person person) {
        List<Pet> result = petHashMap.values().stream()
                .filter(Pet -> (Objects.equals(Pet.getPetOwner().getName(), person.getName()) && Objects.equals(Pet.getPetOwner().getAge(), person.getAge())
                        && Objects.equals(Pet.getPetOwner().getSex(), person.getSex())))
                .collect(Collectors.toList());
        return result;
    }

    public List<Pet> sortPets(Comparator<Pet> P) {
        List<Pet> result = petHashMap.values().stream()
                .sorted(P)
                .collect(Collectors.toList());
        return result;
    }
}
