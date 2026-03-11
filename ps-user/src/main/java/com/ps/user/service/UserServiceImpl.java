package com.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ps.common.constant.SystemConstant;
import com.ps.common.exception.BusinessException;
import com.ps.common.result.RestResult;
import com.ps.common.utils.EncryptUtils;
import com.ps.core.entity.User;
import com.ps.core.enums.UserTypeEnum;
import com.ps.core.vo.UserVO;
import com.ps.security.service.JwtService;
import com.ps.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Map<String, Object> register(String username, String password, String phone, String email) {
        if (getUserByUsername(username) != null) {
            throw BusinessException.of("USER_EXIST", "用户名已存在");
        }

        if (phone != null && getUserByPhone(phone) != null) {
            throw BusinessException.of("PHONE_EXIST", "手机号已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(EncryptUtils.bcrypt(password));
        user.setNickname(username);
        user.setPhone(phone);
        user.setEmail(email);
        user.setUserType(UserTypeEnum.PERSONAL.getCode());
        user.setStatus(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);

        return generateAuthResult(user.getId(), user.getUsername());
    }

    @Override
    public Map<String, Object> login(String username, String password, String loginIp) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw BusinessException.of("USER_NOT_FOUND", "用户不存在");
        }

        if (!EncryptUtils.bcryptCheck(password, user.getPassword())) {
            throw BusinessException.of("PASSWORD_ERROR", "密码错误");
        }

        if (user.getStatus() != null && user.getStatus() == 1) {
            throw BusinessException.of("USER_DISABLED", "账户已被禁用");
        }

        user.setLastLoginIp(loginIp);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLoginFailCount(0);
        userMapper.updateById(user);

        return generateAuthResult(user.getId(), user.getUsername());
    }

    @Override
    public void logout(Long userId) {
        String tokenKey = SystemConstant.REDIS_TOKEN_PREFIX + userId;
        String userKey = SystemConstant.REDIS_USER_PREFIX + userId;
        redisTemplate.delete(tokenKey);
        redisTemplate.delete(userKey);
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw BusinessException.of("TOKEN_INVALID", "无效的刷新令牌");
        }

        Long userId = jwtService.getUserId(refreshToken);
        String username = jwtService.getUsername(refreshToken);

        return generateAuthResult(userId, username);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw BusinessException.of("USER_NOT_FOUND", "用户不存在");
        }

        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        vo.setGender(user.getGender());
        vo.setUserType(user.getUserType());
        vo.setEnterpriseId(user.getEnterpriseId());
        vo.setStatus(user.getStatus());
        return vo;
    }

    private Map<String, Object> generateAuthResult(Long userId, String username) {
        String token = jwtService.generateToken(userId, username, null);
        String refreshToken = jwtService.generateRefreshToken(userId, username);

        String tokenKey = SystemConstant.REDIS_TOKEN_PREFIX + userId;
        String userKey = SystemConstant.REDIS_USER_PREFIX + userId;

        redisTemplate.opsForValue().set(tokenKey, token, 2, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(userKey, getUserById(userId), 24, TimeUnit.HOURS);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("refreshToken", refreshToken);
        result.put("expireTime", jwtService.getExpire());
        result.put("userId", userId);
        result.put("username", username);

        return result;
    }
}
