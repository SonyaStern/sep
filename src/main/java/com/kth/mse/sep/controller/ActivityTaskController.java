package com.kth.mse.sep.controller;

import com.kth.mse.sep.converter.ModelConvertor;
import com.kth.mse.sep.dto.ActivityTaskDto;
import com.kth.mse.sep.model.ActivityTask;
import com.kth.mse.sep.repository.ActivityTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityTaskController {

    private final ActivityTaskRepository activityTaskRepository;

    private final ModelConvertor convertor;

    @GetMapping("/{activityId}")
    public ActivityTaskDto getEmployeeById(@PathVariable("activityId") Long activityId) {
        Optional<ActivityTask> activityTask = activityTaskRepository.findById(activityId);
        return activityTask.map(convertor::convertActivityTaskEntityToDto).orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ActivityTaskDto createEmployee(@RequestBody ActivityTaskDto activityTask) {
        ActivityTask effectiveTask = activityTaskRepository.save(convertor.convertActivityTaskToEntity(activityTask));
        return convertor.convertActivityTaskEntityToDto(effectiveTask);
    }

    @PatchMapping("/{activityId}")
    public ActivityTask patchEmployee(@PathVariable("activityId") Long activityId,
                                      @RequestBody ActivityTaskDto activityTask) {
        Optional<ActivityTask> activityTaskToUpdate = activityTaskRepository.findById(activityId);
        return activityTaskToUpdate.map(task -> activityTaskRepository.save(convertor.enrichEmployeeEntity(task, activityTask))).orElse(null);
    }
}
