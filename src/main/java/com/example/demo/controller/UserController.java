package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.dto.UserStatsDTO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    // 跳转到注册页面
    @GetMapping("/toRegister")
    public String toRegister() {
        return "user/register";
    }

    // 跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin() {
        return "user/login";
    }

    // 处理注册请求（页面表单提交）
    @PostMapping("/register")
    public String register(User user, Model model) {
        boolean success = userService.register(user);
        if (success) {
            return "redirect:/user/toLogin?msg=success";  // 重定向到登录页
        } else {
            model.addAttribute("msg", "用户名已存在！");
            return "user/register";
        }
    }

    // 处理登录请求
    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loginUser", user);  // 保存登录状态
            return "redirect:/";  // 登录成功跳转到首页
        } else {
            model.addAttribute("error", "用户名或密码错误！");
            return "user/login";
        }
    }
    
    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }
    
    // 个人信息页面
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/toLogin";
        }
        
        // 获取用户统计信息
        UserStatsDTO stats = userService.getUserStats();
        model.addAttribute("stats", stats);
        
        return "user/profile";
    }
    
    // 更新个人信息
    @PostMapping("/updateProfile")
    public String updateProfile(String email, String phone, String realName, 
                              HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/toLogin";
        }
        
        // 更新用户信息
        loginUser.setEmail(email);
        loginUser.setPhone(phone);
        loginUser.setRealName(realName);
        
        boolean success = userService.updateProfile(loginUser);
        if (success) {
            // 更新session中的用户信息
            User updatedUser = userService.findById(loginUser.getId());
            session.setAttribute("loginUser", updatedUser);
            model.addAttribute("msg", "个人信息更新成功！");
        } else {
            model.addAttribute("error", "更新失败，请重试！");
        }
        
        // 重新获取统计信息
        UserStatsDTO stats = userService.getUserStats();
        model.addAttribute("stats", stats);
        
        return "user/profile";
    }
    
    // 修改密码
    @PostMapping("/changePassword")
    public String changePassword(String oldPassword, String newPassword, String confirmPassword,
                               HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/toLogin";
        }
        
        // 验证新密码确认
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的新密码不一致！");
            UserStatsDTO stats = userService.getUserStats();
            model.addAttribute("stats", stats);
            return "user/profile";
        }
        
        boolean success = userService.changePassword(loginUser.getId(), oldPassword, newPassword);
        if (success) {
            model.addAttribute("msg", "密码修改成功！");
        } else {
            model.addAttribute("error", "当前密码错误！");
        }
        
        // 重新获取统计信息
        UserStatsDTO stats = userService.getUserStats();
        model.addAttribute("stats", stats);
        
        return "user/profile";
    }

    // API接口（供Postman测试）
    @PostMapping("/api/register")
    @ResponseBody
    public String apiRegister(@RequestBody User user) {
        boolean success = userService.register(user);
        if (success) {
            return "{\"code\": 200, \"msg\": \"注册成功\"}";
        } else {
            return "{\"code\": 400, \"msg\": \"用户名已存在\"}";
        }
    }
}
