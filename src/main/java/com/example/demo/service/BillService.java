package com.example.demo.service;

import com.example.demo.dto.StatisticsDTO;
import com.example.demo.entity.Bill;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BillService {
    PageInfo<Bill> search(Bill bill, Integer pageNum, Integer pageSize);
    Bill getById(String id);  // 根据ID查询
    List<Bill> getAll();
    void insert(Bill bill);
    void update(Bill bill);  // 更新账单
    void removeById(String id);
    void batchDelete(String[] ids);
    
    // 统计方法
    List<StatisticsDTO> getMonthlyStats(int year);
    List<StatisticsDTO> getDailyStats(int year, int month);
    List<StatisticsDTO> getYearlyStats();
    List<StatisticsDTO> getCategoryStats(Integer year, Integer month);
}
