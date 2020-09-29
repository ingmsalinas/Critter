package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepository.findByPets_Id(petId);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepository.findByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Schedule> schedules = new ArrayList<>();
        List<Pet> pets = petRepository.findByCustomer_Id(customerId);

        pets.forEach(pet -> {
            List<Schedule> petSchedules = getScheduleForPet(pet.getId());
            schedules.addAll(petSchedules);
        });

        return schedules;
    }

}
