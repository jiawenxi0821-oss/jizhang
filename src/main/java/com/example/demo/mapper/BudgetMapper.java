package com.example.demo.mapper;

import com.example.demo.entity.Budget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BudgetMapper {
    List<Budget> getAll();
    Budget getById(Integer id);
    Budget getByCondition(@Param("year") Integer year, @Param("month") Integer month, @Param("typeId") Integer typeId);
    void save(Budget budget);
    void update(Budget budget);
    void deleteById(Integer id);
    
    // 获取某月支出总额
    Double getMonthlyExpense(@Param("year") int year, @Param("month") int month);
    // 获取某年支出总额
    Double getYearlyExpense(@Param("year") int year);
    // 获取某月某分类支出
    Double getCategoryExpense(@Param("year") int year, @Param("month") Integer month, @Param("typeId") int typeId);
}
