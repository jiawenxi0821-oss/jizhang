package com.example.demo.service.impl;

import com.example.demo.entity.BillType;
import com.example.demo.mapper.BillTypeMapper;
import com.example.demo.service.BillTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("billTypeService")
public class BillTypeServiceImpl implements BillTypeService {
    @Resource
    private BillTypeMapper billTypeMapper;

    @Override
    public List<BillType> search() {
        return billTypeMapper.get();
    }

    @Override
    public BillType getById(Integer id) {
        return billTypeMapper.getById(id);
    }

    @Override
    public List<BillType> getWithBillCount() {
        return billTypeMapper.getWithBillCount();
    }

    @Override
    public void save(BillType billType) {
        billTypeMapper.save(billType);
    }

    @Override
    public void update(BillType billType) {
        billTypeMapper.update(billType);
    }

    @Override
    public boolean deleteById(Integer id) {
        // 检查该分类下是否有账单
        int count = billTypeMapper.countBillsByTypeId(id);
        if (count > 0) {
            return false;  // 有关联账单，不能删除
        }
        billTypeMapper.deleteById(id);
        return true;
    }
}
