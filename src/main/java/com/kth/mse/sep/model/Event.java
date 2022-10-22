package com.kth.mse.sep.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "event")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Event extends Request {

    @Column(name = "event_date")
    private Timestamp eventDate;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "financial_feedback")
    private String financialFeedback;
}
