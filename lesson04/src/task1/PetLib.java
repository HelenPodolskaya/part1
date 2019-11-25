package task1;

import sun.security.util.PendingException;
import task3.Person;

import java.util.*;
import java.util.stream.Collectors;

public class PetLib implements IPetLib<Pet> {
    private Map<Integer, Pet> petHashMap;

    @Override
    public Map<Integer, Pet> getPetHashMap() {
        return petHashMap;
    }

    public PetLib() {
        petHashMap = new HashMap<>();
    }

    @Override
    public boolean addPet(Pet pet) throws PetLibException {
        if (isPetInMapByName(findPetByOwner(pet.getPetOwner()), pet.getPetName())) {
            throw new PetLibException("Такое животное уже есть!");
        } else {
            petHashMap.put(pet.getPetID(), pet);
            return true;
        }
    }

    @Override
    public boolean setPetName(Integer petID, String petName) throws PetLibException {
        if (petHashMap.containsKey(petID)) {
            Pet pet = petHashMap.get(petID);
            pet.setPetName(petName);
            return true;
        } else throw new PetLibException("Животное с таким идентификатором не найдено!");
    }

    @Override
    public boolean setPetWeight(Integer petID, Double petWeight) throws PetLibException {
        if (petHashMap.containsKey(petID)) {
            Pet pet = petHashMap.get(petID);
            pet.setPetWeight(petWeight);
            return true;
        } else throw new PetLibException("Животное с таким идентификатором не найдено!");
    }

    @Override
    public List<Pet> findPet(String petName) throws PetLibException {
        List<Pet> result = petHashMap.values().stream()
                .filter(Pet -> Objects.equals(Pet.getPetName(), petName))
                .collect(Collectors.toList());
        if (result.size() == 0) throw new PetLibException("Животных с таким именем не найдено!");
        return result;
    }

    private boolean isPetInMapByName(List<Pet> resPets, String petName) {
        ListIterator<Pet> itr = resPets.listIterator();
        while (itr.hasNext()) {
            if (itr.next().getPetName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    private List<Pet> findPetByOwner(Person person) {
        List<Pet> result = petHashMap.values().stream()
                .filter(Pet -> (Objects.equals(Pet.getPetOwner().getName(), person.getName()) && Objects.equals(Pet.getPetOwner().getAge(), person.getAge())
                        && Objects.equals(Pet.getPetOwner().getSex(), person.getSex())))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Pet> sortPets(Comparator<Pet> P) {
        List<Pet> result = petHashMap.values().stream()
                .sorted(P)
                .collect(Collectors.toList());
        return result;
    }

}
