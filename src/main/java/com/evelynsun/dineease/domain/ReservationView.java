package com.evelynsun.dineease.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/*
 * @author evelynsun
 */
public class ReservationView {
    private String table;
    private Integer table_id;
    private String code;
    private Date reservation_date;
    private Time reservation_time;

    private enum status {
        RESERVED, CHECKED_IN, CANCELLED
    }

    private status reserve_status;
    private String customer_name;
    private String customer_num;
    private Timestamp checkin_time;
    private String remark;
    private Timestamp created_at;
    private Timestamp updated_at;

    public ReservationView() {
    }

    public Integer getTable_id() {
        return table_id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public Time getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(Time reservation_time) {
        this.reservation_time = reservation_time;
    }

    public status getReserve_status() {
        return reserve_status;
    }

    public void setReserve_status(status reserve_status) {
        this.reserve_status = reserve_status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_num() {
        return customer_num;
    }

    public void setCustomer_num(String customer_num) {
        this.customer_num = customer_num;
    }

    public Timestamp getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Timestamp checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
