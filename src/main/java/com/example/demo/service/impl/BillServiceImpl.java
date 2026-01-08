package com.example.demo.service.impl;

import com.example.demo.dto.StatisticsDTO;
import com.example.demo.entity.Bill;
import com.example.demo.mapper.BillMapper;
import com.example.demo.service.BillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("helloService")
public class BillServiceImpl implements BillService {
    @Resource
    private BillMapper billMapper;

    @Override
    public PageInfo<Bill> search(Bill bill, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bill> list = billMapper.get(bill);
        return new PageInfo<>(list);
    }

    @Override
    public Bill getById(String id) {
        return billMapper.getById(id);
    }

    @Override
    public List<Bill> getAll() {
        return billMapper.getAll();
    }

    @Override
    public void insert(Bill bill) {
        billMapper.save(bill);
    }

    @Override
    public void update(Bill bill) {
        billMapper.update(bill);
    }

    @Override
    public void removeById(String id) {
        billMapper.deleteById(id);
    }

    @Override
    public void batchDelete(String[] ids) {
        billMapper.batchDelete(ids);
    }

    @Override
    public List<StatisticsDTO> getMonthlyStats(int year) {
        return billMapper.getMonthlyStats(year);
    }

    @Override
    public List<StatisticsDTO> getDailyStats(int year, int month) {
        return billMapper.getDailyStats(year, month);
    }

    @Override
    public List<StatisticsDTO> getYearlyStats() {
        return billMapper.getYearlyStats();
    }

    @Override
    public List<StatisticsDTO> getCategoryStats(Integer year, Integer month) {
        return billMapper.getCategoryStats(year, month);
    }
}
