package com.example.demo.mapper;

import com.example.demo.dto.StatisticsDTO;
import com.example.demo.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillMapper {
    List<Bill> get(Bill bill);
    Bill getById(String id);  // 根据ID查询
    List<Bill> getAll();  // 获取所有账单（用于导出）
    void save(Bill bill);
    void update(Bill bill);  // 更新账单
    void deleteById(String id);
    void batchDelete(String[] ids);
    
    // 统计方法
    List<StatisticsDTO> getMonthlyStats(@Param("year") int year);  // 按月统计
    List<StatisticsDTO> getDailyStats(@Param("year") int year, @Param("month") int month);  // 按日统计
    List<StatisticsDTO> getYearlyStats();  // 按年统计
    List<StatisticsDTO> getCategoryStats(@Param("year") Integer year, @Param("month") Integer month);  // 按分类统计
}
