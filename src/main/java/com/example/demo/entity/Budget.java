package com.example.demo.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Budget {
    private Integer id;
    
    @NotNull(message = "请选择年份")
    private Integer year;
    
    private Integer month;  // null表示年度预算
    
    private Integer typeId;  // null表示总预算，非null表示分类预算
    
    @NotNull(message = "预算金额不能为空")
    @Positive(message = "预算金额必须大于0")
    private Double amount;
    
    // 用于展示
    private String typeName;
    private Double spent;      // 已花费
    private Double remaining;  // 剩余
    private Double percentage; // 使用百分比

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }
    public Double getRemaining() { return remaining; }
    public void setRemaining(Double remaining) { this.remaining = remaining; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
