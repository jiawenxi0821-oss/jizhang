package com.example.demo.service;

import com.example.demo.entity.Budget;
import java.util.List;

public interface BudgetService {
    List<Budget> getAll();
    Budget getById(Integer id);
    List<Budget> getBudgetsWithProgress(int year, Integer month);
    void saveOrUpdate(Budget budget);
    void deleteById(Integer id);
}
