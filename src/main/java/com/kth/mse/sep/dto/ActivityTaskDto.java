package com.kth.mse.sep.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Data
public class ActivityTaskDto extends RequestDto {

    private Timestamp plannedStartDate;

    private Timestamp plannedEndDate;

    private String clientNeeds;

    @Nullable
    private String plan;

    private String department;

    @Nullable
    private String comment;
}
