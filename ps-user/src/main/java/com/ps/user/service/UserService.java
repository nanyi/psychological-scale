package com.ps.user.service;

import com.ps.core.entity.User;
import com.ps.user.vo.UserVO;

import java.util.Map;

public interface UserService {

    Map<String, Object> register(String username, String password, String phone, String email);

    Map<String, Object> login(String username, String password, String loginIp);

    void logout(Long userId);

    Map<String, Object> refreshToken(String refreshToken);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByPhone(String phone);

    UserVO getUserInfo(Long userId);
}
