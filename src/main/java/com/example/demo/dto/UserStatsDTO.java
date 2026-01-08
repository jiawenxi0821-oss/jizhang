package com.example.demo.dto;

public class UserStatsDTO {
    private Integer totalBills;
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;

    public UserStatsDTO() {}

    public UserStatsDTO(Integer totalBills, Double totalIncome, Double totalExpense) {
        this.totalBills = totalBills;
        this.totalIncome = totalIncome != null ? totalIncome : 0.0;
        this.totalExpense = totalExpense != null ? totalExpense : 0.0;
        this.balance = this.totalIncome - this.totalExpense;
    }

    public Integer getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(Integer totalBills) {
        this.totalBills = totalBills;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}