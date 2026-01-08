package com.example.demo.service.impl;

import com.example.demo.entity.Budget;
import com.example.demo.mapper.BudgetMapper;
import com.example.demo.service.BudgetService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    @Resource
    private BudgetMapper budgetMapper;

    @Override
    public List<Budget> getAll() {
        return budgetMapper.getAll();
    }

    @Override
    public Budget getById(Integer id) {
        return budgetMapper.getById(id);
    }

    @Override
    public List<Budget> getBudgetsWithProgress(int year, Integer month) {
        List<Budget> budgets = budgetMapper.getAll();
        List<Budget> result = new ArrayList<>();
        
        for (Budget budget : budgets) {
            // 只返回匹配年份的预算
            if (!budget.getYear().equals(year)) continue;
            // 如果指定了月份，只返回该月或年度预算
            if (month != null && budget.getMonth() != null && !budget.getMonth().equals(month)) continue;
            
            Double spent = 0.0;
            if (budget.getTypeId() == null) {
                // 总预算
                if (budget.getMonth() != null) {
                    spent = budgetMapper.getMonthlyExpense(budget.getYear(), budget.getMonth());
                } else {
                    spent = budgetMapper.getYearlyExpense(budget.getYear());
                }
            } else {
                // 分类预算
                spent = budgetMapper.getCategoryExpense(budget.getYear(), budget.getMonth(), budget.getTypeId());
            }
            
            budget.setSpent(spent != null ? spent : 0.0);
            budget.setRemaining(budget.getAmount() - budget.getSpent());
            budget.setPercentage(budget.getAmount() > 0 ? (budget.getSpent() / budget.getAmount()) * 100 : 0);
            
            result.add(budget);
        }
        return result;
    }

    @Override
    public void saveOrUpdate(Budget budget) {
        Budget existing = budgetMapper.getByCondition(budget.getYear(), budget.getMonth(), budget.getTypeId());
        if (existing != null) {
            existing.setAmount(budget.getAmount());
            budgetMapper.update(existing);
        } else {
            budgetMapper.save(budget);
        }
    }

    @Override
    public void deleteById(Integer id) {
        budgetMapper.deleteById(id);
    }
}
