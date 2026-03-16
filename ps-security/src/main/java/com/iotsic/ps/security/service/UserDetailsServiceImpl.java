package com.iotsic.ps.security.service;

import com.iotsic.ps.api.dto.UserResponse;
import com.iotsic.ps.api.user.UserApi;
import com.iotsic.ps.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserApi userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("加载用户：{}", username);

        UserResponse user = userService.getUserByUsername(username).getData();
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在：" + username);
        }

        User userEntity = new User();
        BeanUtils.copyProperties(user, User.class);
        return new UserDetailsImpl(userEntity, java.util.List.of());
    }
}
