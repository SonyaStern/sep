package com.kth.mse.sep.dto;

import com.kth.mse.sep.model.StatusEnum;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDto {

    private Long requestId;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private StatusEnum status = StatusEnum.OPEN;
}
