package com.kth.mse.sep.converter;

import com.kth.mse.sep.dto.ActivityTaskDto;
import com.kth.mse.sep.dto.ClientDto;
import com.kth.mse.sep.dto.EmployeeDto;
import com.kth.mse.sep.dto.EventDto;
import com.kth.mse.sep.model.ActivityTask;
import com.kth.mse.sep.model.Client;
import com.kth.mse.sep.model.Employee;
import com.kth.mse.sep.model.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class ModelConvertor {

    private final ModelMapper modelMapper;

    public Employee convertEmployeeDtoToEntity(EmployeeDto employee) {
        return modelMapper.map(employee, Employee.class);
    }

    public EmployeeDto convertEmployeeEntityToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public Employee enrichEmployeeEntity(Employee employee, EmployeeDto employeeDto) {
        return employee
                .setFirstName(employeeDto.getFirstName())
                .setLastName(employeeDto.getLastName())
                .setEmail(employeeDto.getEmail())
                .setDepartment(employeeDto.getDepartment())
                .setPosition(employeeDto.getPosition());
    }

    public Client convertEmployeeDtoToEntity(ClientDto client) {
        return modelMapper.map(client, Client.class);
    }

    public ClientDto convertEmployeeEntityToDto(Client client) {
        return modelMapper.map(client, ClientDto.class);
    }

    public Client enrichClientEntity(Client client, ClientDto clientDto) {
        return client
                .setFirstName(clientDto.getFirstName())
                .setLastName(clientDto.getLastName())
                .setEmail(clientDto.getEmail())
                .setPhoneNumber(clientDto.getPhoneNumber())
                .setAddress(clientDto.getAddress());
    }

    public Event convertEventDtoToEntity(EventDto event) {
        return modelMapper.map(event, Event.class);
    }

    public EventDto convertEventEntityToDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    public Event enrichEventEntity(Event event, EventDto eventDto) {
        event
                .setCreateDate(eventDto.getCreateDate())
                .setLastUpdateDate(eventDto.getLastUpdateDate())
                .setStatus(eventDto.getStatus());
        return event
                .setEventDate(eventDto.getEventDate())
                .setAddress(eventDto.getAddress())
                .setClient(eventDto.getClient())
                .setFinancialFeedback(eventDto.getFinancialFeedback());
    }

    public ActivityTask convertActivityTaskToEntity(ActivityTaskDto activityTask) {
        ActivityTask convertedActivityTask = modelMapper.map(activityTask, ActivityTask.class);
        convertedActivityTask.setCreateDate(nonNull(activityTask.getCreateDate()) ? activityTask.getCreateDate() : Timestamp.from(Instant.now()));
        convertedActivityTask.setLastUpdateDate(Timestamp.from(Instant.now()));
        return convertedActivityTask;
    }

    public ActivityTaskDto convertActivityTaskEntityToDto(ActivityTask activityTask) {
        return modelMapper.map(activityTask, ActivityTaskDto.class);
    }

    public ActivityTask enrichEmployeeEntity(ActivityTask activityTask, ActivityTaskDto activityTaskDto) {
        activityTask
                .setRequestId(activityTaskDto.getRequestId())
                .setCreateDate(activityTaskDto.getCreateDate())
                .setLastUpdateDate(Timestamp.from(Instant.now()))
                .setStatus(activityTaskDto.getStatus());
        return activityTask
                .setPlannedStartDate(activityTask.getPlannedStartDate())
                .setPlannedEndDate(activityTask.getPlannedEndDate())
                .setClientNeeds(activityTask.getClientNeeds())
                .setPlan(activityTask.getPlan())
                .setDepartment(activityTask.getDepartment())
                .setComment(activityTask.getComment());

    }
}
