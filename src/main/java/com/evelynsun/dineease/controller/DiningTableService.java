package com.evelynsun.dineease.controller;

import com.evelynsun.dineease.dao.DiningTableDAO;
import com.evelynsun.dineease.dao.TableTimeSlotDAO;
import com.evelynsun.dineease.domain.DiningTable;
import com.evelynsun.dineease.domain.TableTimeSlot;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.*;

/*
 * @author evelynsun
 */

@RestController
@RequestMapping("/table")
public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();
    private TableTimeSlotDAO tableTimeSlotDAO = new TableTimeSlotDAO();
    private OrderService orderService = new OrderService();

    @PostMapping("/create-table")
    private DiningTable createTable(@RequestBody DiningTable diningTable) {
        diningTableDAO.update("insert into dining_table (`name`, numOfSeats, remark) " +
                        "values (?,?,?)",
                diningTable.getName(), diningTable.getNumOfSeats(), diningTable.getRemark());
        return getTable(diningTable.getId());
    }

    @PutMapping("/edit-table/{tableID}")
    public DiningTable editTable(@PathVariable Integer tableID,
                                 @RequestBody DiningTable diningTable) {
        diningTableDAO.update("update dining_table set name = ?, numOfSeats = ?, " +
                        "remark = ? where id = ?", diningTable.getName(),
                diningTable.getNumOfSeats(), diningTable.getRemark(), tableID);
        return getTable(diningTable.getId());
    }

    @PatchMapping("/activate-table/{tableID}")
    public DiningTable activateTable(@PathVariable Integer tableID) {
        diningTableDAO.update("update dining_table set active_status = 'ACTIVE' where id = ?",
                tableID);
        setTableOpen(tableID);
        return getTable(tableID);
    }

    @PatchMapping("/deactivate-table/{tableID}")
    public DiningTable deactivateTable(@PathVariable Integer tableID) {
        if (getUseStatus(tableID).equals("OPEN")) {
            diningTableDAO.update("update dining_table set active_status = 'INACTIVE' where id = ?",
                    tableID);
            clearUseStatusOpen(tableID);
        }
        return getTable(tableID);
    }

    @PatchMapping("/checkin-table/{tableID}")
    public DiningTable checkinTable(@PathVariable Integer tableID) {
        if (getActiveStatus(tableID).equals("ACTIVE") && getUseStatus(tableID).equals("OPEN")) {
            setUseStatusInUse(tableID);
            //create order
            orderService.createOrder(tableID);
        } else {
            throw new RuntimeException("The table is not available.");
        }
        return getTable(tableID);
    }

    @GetMapping("/{tableID}")
    public DiningTable getDiningTable(@PathVariable Integer tableID) {
        return diningTableDAO.querySingle("select * from dining_table where id = ?",
                DiningTable.class, tableID);
    }

    @GetMapping("/all-tables")
    public List<DiningTable> getTableList() {
        return diningTableDAO.queryList("select * from dining_table",
                DiningTable.class);
    }

    @GetMapping("/active-tables")
    public List<DiningTable> getActiveTableList() {
        return diningTableDAO.queryList("select * from dining_table where active_status = 'ACTIVE'",
                DiningTable.class);
    }

    @GetMapping("/active-table-names")
    public List<String> getActiveTableNames() {
        List<DiningTable> diningTables = diningTableDAO.queryList("select * from dining_table " +
                "where active_status = 'ACTIVE'", DiningTable.class);
        List<String> activeTableNames = new ArrayList<>();
        for (DiningTable diningTable : diningTables) {
            activeTableNames.add(diningTable.getName());
        }
        return activeTableNames;
    }

    @DeleteMapping("/delete-table/{tableID}")
    public void deleteTable(@PathVariable Integer tableID) {
        if (getActiveStatus(tableID).equals("INACTIVE")) {
            diningTableDAO.update("delete from dining_table where id = ?", tableID);
        } else {
            throw new RuntimeException("The table is active and cannot be deleted.");
        }
    }

    public void setTableOpen(Integer tableID) {
        diningTableDAO.update("update dining_table set use_status = 'OPEN' where id = ?",
                tableID);
    }

    public void setUseStatusInUse(Integer tableID) {
        diningTableDAO.update("update dining_table set use_status = 'IN_USE' where id = ?",
                tableID);
    }

    public void clearUseStatusOpen(Integer tableID) {
        diningTableDAO.update("update dining_table set use_status = null where id = ?",
                tableID);
    }

    public DiningTable getTable(Integer tableID) {
        return diningTableDAO.querySingle("select * from dining_table where id = ?",
                DiningTable.class, tableID);
    }

    public String getUseStatus(Integer tableID) {
        return (String) diningTableDAO.queryScaler("select use_status from dining_table where id " +
                "= ?", tableID);
    }

    public String getActiveStatus(Integer tableID) {
        return (String) diningTableDAO.queryScaler("select active_status from dining_table where " +
                "id " +
                "= ?", tableID);
    }


    @PostMapping("/create-timeslot")
    private void createTimeslot(@RequestBody TableTimeSlot tableTimeSlot) {
        tableTimeSlotDAO.update("insert into table_timeslot (table_id, time_slot, remark) " +
                        "values (?,?,?)",
                tableTimeSlot.getTable_id(), tableTimeSlot.getTime_slot(),
                tableTimeSlot.getRemark());
    }

    @DeleteMapping("/delete-timeslot/{slotID}")
    public void deleteTimeslot(@PathVariable Integer slotID) {
        tableTimeSlotDAO.update("delete from table_timeslot where id = ?", slotID);
    }

    @GetMapping("/all-timeslots/{tableID}")
    public List<TableTimeSlot> getTableTimeSlots(@PathVariable Integer tableID) {
        return tableTimeSlotDAO.queryList("select * from table_timeslot where table_id = ?",
                TableTimeSlot.class, tableID);
    }

    @GetMapping("/all-slots/{tableID}")
    public List<Time> getTableSlots(@PathVariable Integer tableID) {
        List<TableTimeSlot> tableTimeSlots = tableTimeSlotDAO.queryList("select * from " +
                        "table_timeslot where table_id = ?",
                TableTimeSlot.class, tableID);
        List<Time> slots = new ArrayList<>();
        for (TableTimeSlot tableTimeSlot : tableTimeSlots) {
            slots.add(tableTimeSlot.getTime_slot());
        }
        return slots;
    }

}
