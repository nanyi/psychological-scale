package com.iotsic.ps.thirdparty.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("third_party_config")
public class ThirdPartyConfig extends BaseEntity {

    private String platformName;

    private String platformCode;

    private String appKey;

    private String appSecret;

    private String apiUrl;

    private String callbackUrl;

    private Integer platformType;

    private String configJson;

    private Integer status;

    private Integer syncInterval;

    private String description;
}
