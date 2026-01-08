package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.dto.UserStatsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void save(User user);
    User findByUsername(String username);
    User findById(Integer id);
    void updateProfile(User user);
    void updatePassword(Integer id, String newPassword);
    UserStatsDTO getUserStats();
}
