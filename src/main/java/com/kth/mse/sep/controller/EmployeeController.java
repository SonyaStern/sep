package com.kth.mse.sep.controller;

import com.kth.mse.sep.converter.ModelConvertor;
import com.kth.mse.sep.dto.EmployeeDto;
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

    private final ModelConvertor convertor;

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.map(convertor::convertEmployeeEntityToDto).orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employee) {
        Employee effectiveEmployee = employeeRepository.save(convertor.convertEmployeeDtoToEntity(employee));
        return convertor.convertEmployeeEntityToDto(effectiveEmployee);
    }

    @PatchMapping("/{employeeId}")
    public EmployeeDto patchEmployee(@PathVariable("employeeId") Long employeeId,
                                  @RequestBody EmployeeDto employee) {
        Optional<Employee> employeeToUpdate = employeeRepository.findById(employeeId);
            return employeeToUpdate.map(eml ->
                    convertor.convertEmployeeEntityToDto(employeeRepository.save(convertor.enrichEmployeeEntity(eml, employee)))).orElse(null);
    }
}
