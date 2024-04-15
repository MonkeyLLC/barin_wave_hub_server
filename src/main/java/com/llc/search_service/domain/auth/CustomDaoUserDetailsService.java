package com.llc.search_service.domain.auth;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.llc.search_service.entity.User;
import com.llc.search_service.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomDaoUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    public CustomDaoUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查用户
        User one = new LambdaQueryChainWrapper<>(userMapper).eq(User::getUsername, username).one();
        if (one == null) {
            throw new UsernameNotFoundException("未能找到用户" + username);
        }
        AuthUser authUser = new AuthUser(one.getUsername(), one.getPassword(), new ArrayList<>());
        authUser.setId(one.getId());
        authUser.setPhone(one.getPhone());
        authUser.setEmail(one.getEmail());
        authUser.setCreatedAt(one.getCreatedAt());
        authUser.setUpdatedAt(one.getUpdatedAt());
        authUser.setLastLoginAt(one.getLastLoginAt());
        return authUser;
    }
}
