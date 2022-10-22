package com.kth.mse.sep.repository;

import com.kth.mse.sep.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
