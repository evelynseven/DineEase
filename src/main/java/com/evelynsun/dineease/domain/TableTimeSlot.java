package com.evelynsun.dineease.domain;

import java.sql.Time;
import java.sql.Timestamp;

/*
 * @author evelynsun
 */
public class TableTimeSlot {
    /*
    * id INT PRIMARY KEY auto_increment,
    table_id INT NOT NULL,
    available_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(128)*/

    private Integer id;
    private Integer table_id;
    private Time time_slot;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String remark;

    public TableTimeSlot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public Time getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(Time time_slot) {
        this.time_slot = time_slot;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
