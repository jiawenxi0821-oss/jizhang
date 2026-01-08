package com.example.demo.mapper;

import com.example.demo.entity.BillType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillTypeMapper {
    List<BillType> get();
    BillType getById(Integer id);
    List<BillType> getWithBillCount();  // 关联查询：分类及其账单数量
    void save(BillType billType);
    void update(BillType billType);
    void deleteById(Integer id);
    int countBillsByTypeId(Integer typeId);  // 查询该分类下的账单数量
}
