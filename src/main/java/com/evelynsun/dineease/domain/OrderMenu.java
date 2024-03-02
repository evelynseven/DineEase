package com.evelynsun.dineease.domain;

import java.sql.Timestamp;

/*
 * @author evelynsun
 */
public class OrderMenu {
    private Integer id;
    private String code;
    private String order_code;
    private Integer menu_id;
    private Integer quantity;

    private enum orderMenuStatus {
        CREATED, UPDATED, CANCELLED
    }

    private orderMenuStatus status;
    private String remark;
    private Timestamp created_at;
    private Timestamp updated_at;

    public OrderMenu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
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

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}
