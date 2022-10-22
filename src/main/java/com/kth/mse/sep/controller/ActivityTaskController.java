package com.kth.mse.sep.controller;

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

    @GetMapping("/{activityId}")
    public ActivityTask getEmployeeById(@PathVariable("activityId") Long activityId) {
        return activityTaskRepository.findById(activityId)
                .orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ActivityTask createEmployee(@RequestBody ActivityTask activityTask) {
        return activityTaskRepository.save(activityTask);
    }

    @PatchMapping("/{activityId}")
    public ActivityTask patchEmployee(@PathVariable("activityId") Long activityId,
                                  @RequestBody ActivityTask activityTask) {
        Optional<ActivityTask>  activityTaskToUpdate = activityTaskRepository.findById(activityId);
        if (activityTaskToUpdate.isPresent()) {
            ActivityTask updatedActivityTask = activityTaskToUpdate.get()
                    .setPlannedStartDate(activityTask.getPlannedStartDate())
                    .setPlannedEndDate(activityTask.getPlannedEndDate())
                    .setClientNeeds(activityTask.getClientNeeds())
                    .setPlan(activityTask.getPlan())
                    .setDepartment(activityTask.getDepartment())
                    .setComment(activityTask.getComment());
            updatedActivityTask
                    .setRequestId(activityTask.getRequestId())
                    .setCreateDate(activityTask.getCreateDate())
                    .setLastUpdateDate(activityTask.getLastUpdateDate())
                    .setStatus(activityTask.getStatus());
            return activityTaskRepository.save(updatedActivityTask);
        }
        return null;
    }
}
