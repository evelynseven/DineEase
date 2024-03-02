package com.evelynsun.dineease.domain;

import java.sql.Timestamp;
import java.util.Set;

/*
 * @author evelynsun
 */
public class DiningTable {
    private Integer id;
    private String name;
    private Integer numOfSeats;

    private enum activeStatus {
        ACTIVE, INACTIVE
    }

    private activeStatus active_status;

    private enum useStatus {
        OPEN, IN_USE
    }

    private useStatus use_status;
    private String remark;
    private Timestamp created_at;
    private Timestamp updated_at;

    public DiningTable() {
    }

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

    public Integer getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(Integer numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public activeStatus getActive_status() {
        return active_status;
    }

    public void setActive_status(activeStatus active_status) {
        this.active_status = active_status;
    }

    public useStatus getUse_status() {
        return use_status;
    }

    public void setUse_status(useStatus use_status) {
        this.use_status = use_status;
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
