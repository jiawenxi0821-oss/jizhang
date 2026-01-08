package com.example.demo.controller;

import com.example.demo.entity.Budget;
import com.example.demo.entity.BillType;
import com.example.demo.service.BudgetService;
import com.example.demo.service.BillTypeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("budget")
public class BudgetController {
    @Resource
    private BudgetService budgetService;
    @Resource
    private BillTypeService billTypeService;

    // 预算列表及执行进度
    @GetMapping("/list")
    public String list(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {
        int currentYear = (year != null) ? year : LocalDate.now().getYear();
        Integer currentMonth = (month != null) ? month : LocalDate.now().getMonthValue();
        
        List<Budget> budgets = budgetService.getBudgetsWithProgress(currentYear, currentMonth);
        List<BillType> billTypeList = billTypeService.search();
        
        model.addAttribute("budgets", budgets);
        model.addAttribute("billTypeList", billTypeList);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", currentMonth);
        
        return "budget/list";
    }

    // 跳转到添加页面
    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("budget", new Budget());
        model.addAttribute("billTypeList", billTypeService.search());
        return "budget/add";
    }

    // 添加/更新预算
    @PostMapping("/save")
    public String save(@Valid Budget budget, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getFieldError().getDefaultMessage());
            model.addAttribute("billTypeList", billTypeService.search());
            return "budget/add";
        }
        budgetService.saveOrUpdate(budget);
        return "redirect:/budget/list";
    }

    // 删除预算
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        budgetService.deleteById(id);
        return "redirect:/budget/list";
    }
}
