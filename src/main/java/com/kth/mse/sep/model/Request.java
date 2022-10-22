package com.kth.mse.sep.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Data
@Accessors(chain = true)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.OPEN;
}
