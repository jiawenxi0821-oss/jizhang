package com.example.demo.service;

import com.example.demo.entity.BillType;

import java.util.List;

public interface BillTypeService {
    List<BillType> search();
    BillType getById(Integer id);
    List<BillType> getWithBillCount();
    void save(BillType billType);
    void update(BillType billType);
    boolean deleteById(Integer id);  // 返回是否删除成功
}
