package com.iotsic.ps.scale.thirdparty.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iotsic.smart.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("third_party_callback")
public class ThirdPartyCallback extends BaseEntity {

    private Long configId;

    private String platformCode;

    private String callbackType;

    private String externalRecordId;

    private String requestData;

    private String responseData;

    private Integer callbackStatus;

    private String errorMessage;

    private LocalDateTime processTime;
}