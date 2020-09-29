package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User {

    private String phoneNumber;

    @Column(length = 500)
    private String notes;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "customer")
    private List<Pet> pets;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
