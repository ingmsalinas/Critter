package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId){
        return customerRepository.findByPets_Id(petId);
    }

    public void addPetToCustomer(Customer customer, Pet pet) {
        List<Pet> pets = customer.getPets();
        if(pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPets(pets);

        customerRepository.save(customer);
    }
}
