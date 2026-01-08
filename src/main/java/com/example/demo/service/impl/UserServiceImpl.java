package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.dto.UserStatsDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            return false;
        }
        user.setCreateTime(new Date());
        userMapper.save(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
    
    @Override
    public boolean updateProfile(User user) {
        try {
            userMapper.updateProfile(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        try {
            User user = userMapper.findById(userId);
            if (user != null && user.getPassword().equals(oldPassword)) {
                userMapper.updatePassword(userId, newPassword);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public UserStatsDTO getUserStats() {
        return userMapper.getUserStats();
    }
}
