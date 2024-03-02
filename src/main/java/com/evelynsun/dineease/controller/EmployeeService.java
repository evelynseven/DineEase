package com.evelynsun.dineease.controller;

import com.evelynsun.dineease.dao.EmployeeDAO;
import com.evelynsun.dineease.domain.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @author evelynsun
 * 通过调用EmployeeDAO对象，完成对Employee表的各种操作
 */

@RestController
@RequestMapping("/employee")
public class EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @GetMapping
    public Employee getEmployee(String username, String password) {

        return employeeDAO.querySingle("select * from employee where username = ? and password = " +
                        "md5(?)",
                Employee.class, username, password);
    }

    @GetMapping("all-employees")
    public List<Employee> getEmployeeList() {

        return employeeDAO.queryList("select * from employee", Employee.class);
    }
}
