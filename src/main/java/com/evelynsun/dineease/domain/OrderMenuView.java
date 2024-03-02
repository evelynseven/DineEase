package com.evelynsun.dineease.domain;

import java.sql.Timestamp;

/*
 * @author evelynsun
 */
public class OrderMenuView {
    private Integer id;
    private String code;
    private String order_code;
    private String name;

    private enum menuType {
        FOOD, DRINKS
    }

    private menuType type;
    private String ingredients;
    private double unit_price;
    private Integer quantity;

    private enum orderMenuStatus {
        CREATED, UPDATED, CANCELLED
    }

    private orderMenuStatus status;
    private String remark;
    private Timestamp created_at;
    private Timestamp updated_at;

    public OrderMenuView() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public menuType getType() {
        return type;
    }

    public void setType(menuType type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public orderMenuStatus getStatus() {
        return status;
    }

    public void setStatus(orderMenuStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
