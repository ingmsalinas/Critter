package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        return convertCustomerToCustomerDTO(customerService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        customers.forEach(customer -> {
            customerDTOS.add(convertCustomerToCustomerDTO(customer));
        });

        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getOwnerByPet(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeesForService(employeeDTO);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        employees.forEach(employee -> {
            employeeDTOS.add(convertEmployeeToEmployeeDTO(employee));
        });

        return employeeDTOS;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();

        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null &&
            !customer.getPets().isEmpty()) {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        List<Pet> pets = new ArrayList<>();

        BeanUtils.copyProperties(customerDTO, customer);
        if(customerDTO.getPetIds() != null &&
            !customerDTO.getPetIds().isEmpty()) {
            customerDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                pets.add(pet);
            });
            customer.setPets(pets);
        }

        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        BeanUtils.copyProperties(employee, employeeDTO);
        if(employee.getSkills() != null &&
            !employee.getSkills().isEmpty()) {
            employeeDTO.setSkills(employee.getSkills());
        }
        if(employee.getDaysAvailable() != null &&
                !employee.getDaysAvailable().isEmpty()) {
            employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        }

        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);
        if(employeeDTO.getSkills() != null &&
                !employeeDTO.getSkills().isEmpty()) {
            employee.setSkills(employeeDTO.getSkills());
        }
        if(employeeDTO.getDaysAvailable() != null &&
                !employeeDTO.getDaysAvailable().isEmpty()) {
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        }

        return employee;
    }

}
