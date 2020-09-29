package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        customerService.addPetToCustomer(savedPet.getCustomer(), savedPet);
        return savedPet;
    }

    public Pet getPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByCustomer_Id(ownerId);
    }

}
