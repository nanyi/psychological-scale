package com.iotsic.ps.thirdparty.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("third_party_sync_log")
public class SyncLog extends BaseEntity {

    private Long configId;

    private String platformCode;

    private Integer syncType;

    private Integer syncStatus;

    private String requestData;

    private String responseData;

    private String errorMessage;

    private Integer syncCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
