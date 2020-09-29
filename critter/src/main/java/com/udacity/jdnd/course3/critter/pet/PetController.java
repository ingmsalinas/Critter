package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        return convertPetToPetDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();

        pets.forEach(pet -> {
            petDTOS.add(convertPetToPetDTO(pet));
        });

        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(ownerId);

        pets.forEach(pet -> {
            petDTOS.add(convertPetToPetDTO(pet));
        });

        return petDTOS;
    }

    public PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet,petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getPetType());

        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO,pet);
        Customer customer = new Customer();
        customer.setId(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet.setPetType(petDTO.getType());

        return pet;
    }

}
