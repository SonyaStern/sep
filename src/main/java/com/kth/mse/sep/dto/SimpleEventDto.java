package com.kth.mse.sep.dto;

import lombok.Data;

@Data
public class SimpleEventDto extends RequestDto {

    private String eventDate;

    private String address;

    private String client;

}
