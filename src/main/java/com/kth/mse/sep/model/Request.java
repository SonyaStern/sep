package com.kth.mse.sep.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.Accessors;

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
