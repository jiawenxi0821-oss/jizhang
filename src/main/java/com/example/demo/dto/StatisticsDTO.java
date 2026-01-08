package com.example.demo.dto;

public class StatisticsDTO {
    private String label;      // 标签（日期/分类名）
    private Double income;     // 收入
    private Double expense;    // 支出
    private Double amount;     // 金额（用于饼图）

    public StatisticsDTO() {}

    public StatisticsDTO(String label, Double amount) {
        this.label = label;
        this.amount = amount;
    }

    public StatisticsDTO(String label, Double income, Double expense) {
        this.label = label;
        this.income = income;
        this.expense = expense;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }
    public Double getExpense() { return expense; }
    public void setExpense(Double expense) { this.expense = expense; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
