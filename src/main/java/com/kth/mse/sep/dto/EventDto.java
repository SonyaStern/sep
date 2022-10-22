package com.kth.mse.sep.dto;

import com.kth.mse.sep.model.Client;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventDto extends RequestDto {

    private Timestamp eventDate;

    private String address;

    private Client client;

    private String financialFeedback;
}
