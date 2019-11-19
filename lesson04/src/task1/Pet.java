package task1;

import task3.Person;

import java.util.Comparator;
import java.util.UUID;

/**
 * @author PodolskayaEV
 * Класс Pet домашнее животное
 * У каждого животного есть:
 * - уникальный идентификационный номер,
 * - кличка,
 * - хозяин (объект класс Person с полями – имя, возраст, пол),
 * - вес.
 */
public class Pet {
    private int petID;
    private String petName;
    private Person petOwner;
    private Double petWeight;

    public Pet(int petID, String petName, Person petOwner, Double petWeight) {
        this.petID = petID;
        this.petName = petName;
        this.petOwner = petOwner;
        this.petWeight = petWeight;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetOwner(Person petOwner) {
        this.petOwner = petOwner;
    }

    public void setPetWeight(Double petWeight) {
        this.petWeight = petWeight;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public Person getPetOwner() {
        return petOwner;
    }

    public Double getPetWeight() {
        return petWeight;
    }
}

