package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BillType {
    private Integer id;

    @NotBlank(message = "分类名称不能为空")
    @Size(min = 1, max = 20, message = "分类名称长度必须在1-20个字符之间")
    private String name;

    // 关联的账单数量（用于关联查询）
    private Integer billCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBillCount() {
        return billCount;
    }

    public void setBillCount(Integer billCount) {
        this.billCount = billCount;
    }
}
