package com.kth.mse.sep.controller;

import com.kth.mse.sep.model.Employee;
import com.kth.mse.sep.repository.EmployeeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PatchMapping("/{employeeId}")
    public Employee patchEmployee(@PathVariable("employeeId") Long employeeId,
                                  @RequestBody Employee employee) {
        Optional<Employee> employeeToUpdate = employeeRepository.findById(employeeId);
        if (employeeToUpdate.isPresent()) {
            Employee updatedEmployee = employeeToUpdate.get()
                    .setFirstName(employee.getFirstName())
                    .setLastName(employee.getLastName())
                    .setEmail(employee.getEmail())
                    .setDepartment(employee.getDepartment())
                    .setPosition(employee.getPosition());
            return employeeRepository.save(updatedEmployee);
        }
        return null;
    }
}
