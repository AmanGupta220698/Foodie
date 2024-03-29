package com.aman.androidfoodie.Model;

import java.util.List;

/**
 * Created by Aman on 4/25/2019.
 */

public class Request {
    private  String phone;
    private String name;
    private String address;
    private  String total;
    private List<Order> foods;
    private  String status;

    public Request() {
    }

    public Request(String phone, String name, String address ,String total ,List<Order> foods) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.total = total;
        this.foods = foods;
        this.status="0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
