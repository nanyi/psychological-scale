package com.iotsic.ps.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private Integer gender;
    private LocalDateTime birthday;
    private Integer userType;
    private Long enterpriseId;
    private Long departmentId;
    private Integer status;
    private LocalDateTime lastLoginTime;
}
