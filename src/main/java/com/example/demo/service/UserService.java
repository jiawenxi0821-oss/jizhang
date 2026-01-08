package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.dto.UserStatsDTO;

public interface UserService {
    boolean register(User user);
    User findByUsername(String username);
    User findById(Integer id);
    boolean updateProfile(User user);
    boolean changePassword(Integer userId, String oldPassword, String newPassword);
    UserStatsDTO getUserStats();
}
