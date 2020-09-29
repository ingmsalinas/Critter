package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();

        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });

        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);

        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });

        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);

        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });

        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);

        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });

        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        BeanUtils.copyProperties(schedule, scheduleDTO);
        if(schedule.getEmployees() != null &&
            !schedule.getEmployees().isEmpty()) {
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        if(schedule.getPets() != null &&
            !schedule.getPets().isEmpty()) {
            schedule.getPets().forEach(pet ->{
                petIds.add(pet.getId());
            });
            scheduleDTO.setPetIds(petIds);
        }

        if(schedule.getActivities() != null &&
            !schedule.getActivities().isEmpty()) {
            scheduleDTO.setActivities(schedule.getActivities());
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        schedule.setEmployees(new ArrayList<>());
        schedule.setPets(new ArrayList<>());

        BeanUtils.copyProperties(scheduleDTO, schedule);
        if(scheduleDTO.getEmployeeIds() != null &&
                !scheduleDTO.getEmployeeIds().isEmpty()) {
            scheduleDTO.getEmployeeIds().forEach(employeeId -> {
                Employee employee = new Employee();
                employee.setId(employeeId);
                employees.add(employee);
            });
            schedule.setEmployees(employees);
        }

        if(scheduleDTO.getPetIds() != null &&
                !scheduleDTO.getPetIds().isEmpty()) {
            scheduleDTO.getPetIds().forEach(petId ->{
                Pet pet = new Pet();
                pet.setId(petId);
                pets.add(pet);
            });
            schedule.setPets(pets);
        }

        return schedule;
    }

}
