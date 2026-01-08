package com.example.demo.controller;

import com.example.demo.entity.BillType;
import com.example.demo.service.BillTypeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("billType")
public class BillTypeController {
    @Resource
    private BillTypeService billTypeService;

    // 分类列表（带账单数量）
    @GetMapping("/list")
    public String list(Model model) {
        List<BillType> list = billTypeService.getWithBillCount();
        model.addAttribute("billTypeList", list);
        return "billType/list";
    }

    // 跳转到添加页面
    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("billType", new BillType());
        return "billType/add";
    }

    // 添加分类
    @PostMapping("/add")
    public String add(@Valid BillType billType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getFieldError().getDefaultMessage());
            return "billType/add";
        }
        billTypeService.save(billType);
        return "redirect:/billType/list";
    }

    // 跳转到编辑页面
    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) {
        BillType billType = billTypeService.getById(id);
        model.addAttribute("billType", billType);
        return "billType/update";
    }

    // 更新分类
    @PostMapping("/update")
    public String update(@Valid BillType billType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getFieldError().getDefaultMessage());
            return "billType/update";
        }
        billTypeService.update(billType);
        return "redirect:/billType/list";
    }

    // 删除分类
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        boolean success = billTypeService.deleteById(id);
        if (!success) {
            // 删除失败，返回列表并显示错误
            List<BillType> list = billTypeService.getWithBillCount();
            model.addAttribute("billTypeList", list);
            model.addAttribute("error", "该分类下有账单，无法删除！");
            return "billType/list";
        }
        return "redirect:/billType/list";
    }
}
