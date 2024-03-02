package com.evelynsun.dineease.domain;

import org.springframework.web.bind.annotation.GetMapping;

/*
 * @author evelynsun
 */
public class Employee {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer positionID;
    private String phoneNum;
    private String note;

    public Employee() {
    }

    public Employee(Integer id, String username, String password, String name, Integer positionID
            , String phoneNum, String note) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.positionID = positionID;
        this.phoneNum = phoneNum;
        this.note = note;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", positionID=" + positionID +
                ", phoneNum='" + phoneNum + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
