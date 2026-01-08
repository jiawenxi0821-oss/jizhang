package com.example.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Bill {
    private Integer id;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private Date billTime;
    private Integer typeId;
    private double price;
    private String explains;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date1;//开始时间

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date2;//结束时间





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }


}



