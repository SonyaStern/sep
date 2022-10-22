package com.kth.mse.sep.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "activity")
@Data
@Accessors(chain = true)
public class ActivityTask extends Request {

    @Column(name = "start_date")
    private Timestamp plannedStartDate;

    @Column(name = "end_date")
    private Timestamp plannedEndDate;

    @Column(name = "client_needs")
    private String clientNeeds;

    @Column(name = "plan")
    private String plan;

    @Column(name = "department")
    private String department;

    @Column(name = "comment")
    private String comment;
}
