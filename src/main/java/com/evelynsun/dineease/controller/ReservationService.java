package com.evelynsun.dineease.controller;

import com.evelynsun.dineease.dao.ReservationDAO;
import com.evelynsun.dineease.domain.Order;
import com.evelynsun.dineease.domain.ReservationView;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * @author evelynsun
 */
@RestController
@RequestMapping("/reservation")
public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private DiningTableService diningTableService = new DiningTableService();
    private OrderService orderService = new OrderService();

    @PostMapping("/create-reservation")
    public ReservationView createReservation(@RequestBody ReservationView reservationView) {
        if (diningTableService.getActiveStatus(reservationView.getTable_id()).equals("ACTIVE") && diningTableService.getUseStatus(reservationView.getTable_id()).equals("OPEN")) {
            String code = UUID.randomUUID().toString();
            reservationDAO.update("insert into reservation (code, table_id, reservation_date, " +
                            "reservation_time, customer_name, customer_num, remark) values (?,?," +
                            "?,?," +
                            "?,?,?)", code, reservationView.getTable_id(),
                    reservationView.getReservation_date(),
                    reservationView.getReservation_time(), reservationView.getCustomer_name(),
                    reservationView.getCustomer_num(), reservationView.getRemark());
            return getRV(code);
        } else {
            throw new RuntimeException("The table is not available for reservation.");
        }
    }

    @PatchMapping("/checkin/{code}")
    public ReservationView checkinReservation(@PathVariable String code) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        reservationDAO.update("update reservation set reserve_status = 'CHECKED_IN', checkin_time" +
                " = ? where code = ?", now, code);
        Integer tableID = getRV(code).getTable_id();
        diningTableService.setUseStatusInUse(tableID);

        //create order after check-in
        Order order = orderService.createOrder(tableID);
        //put customer info into the order
        orderService.updateCustomerInfo(getRV(code).getCustomer_name(),
                getRV(code).getCustomer_num(), order.getCode());

        return getRV(code);
    }

    @PatchMapping("/cancel/{code}")
    public ReservationView cancelReservation(@PathVariable String code) {
        reservationDAO.update("update reservation set reserve_status = 'CANCELLED' where code = " +
                "?", code);
        return getRV(code);
    }

    @GetMapping("/rv-list")
    public List<ReservationView> getRVList() {
        LocalDate currentDate = LocalDate.now();
        return reservationDAO.queryList("select * from reservation_view", ReservationView.class);
    }

    @GetMapping("/future-rv-list")
    public List<ReservationView> getFutureRVList() {
        LocalDate currentDate = LocalDate.now();
        return reservationDAO.queryList("select * from reservation_view where reservation_date > ?",
                ReservationView.class, currentDate);
    }

    @GetMapping("/future-rv-list-by-phone/{phoneNum}")
    public List<ReservationView> getFutureRVListByPhone(@PathVariable String phoneNum) {
        LocalDate currentDate = LocalDate.now();
        return reservationDAO.queryList("select * from reservation_view where reservation_date > " +
                "? and customer_num =" +
                " ?", ReservationView.class, currentDate, phoneNum);
    }

    @GetMapping("/future-rv-list-by-table/{tableID}")
    public List<ReservationView> getFutureRVListByTable(@PathVariable Integer tableID) {
        LocalDate currentDate = LocalDate.now();
        return reservationDAO.queryList("select * from reservation_view where reservation_date > " +
                        "? and table_id = ?",
                ReservationView.class, currentDate, tableID);
    }

    @GetMapping("/future-next-rv-time/{tableID}")
    public Timestamp getFutureNextRVTimeByTable(@PathVariable Integer tableID) {
        LocalDate currentDate = LocalDate.now();
        Date date = (Date) reservationDAO.queryScaler("select reservation_date from " +
                "reservation_view where table_id = ? and reservation_date > ? order by " +
                "reservation_date desc, reservation_time desc limit 1", tableID, currentDate);
        Time time = (Time) reservationDAO.queryScaler("select reservation_time from " +
                "reservation_view where table_id = ? and reservation_date > ? order by " +
                "reservation_date desc, reservation_time desc limit 1", tableID, currentDate);
        if (date != null && time != null) {
            return Timestamp.valueOf(date + " " + time);
        } else return Timestamp.valueOf("1970-01-01 00:00:00");
    }

    @GetMapping("/rv-times")
    public List<Time> getRVTimeByTableAndDate(@RequestParam Integer tableID,
                                              @RequestParam Date reservationDate) {
        List<ReservationView> reservations = reservationDAO.queryList("select * from " +
                        "reservation_view where table_id = ? and reservation_date = ?",
                ReservationView.class, tableID, reservationDate);
        List<Time> times = new ArrayList<>();
        for (ReservationView reservationView : reservations) {
            times.add(reservationView.getReservation_time());
        }
        return times;
    }

    @DeleteMapping("delete/{code}")
    public void deleteRV(@PathVariable String code) {
        reservationDAO.update("delete from reservation where code = ?", code);
    }

    public ReservationView getRV(String code) {
        return reservationDAO.querySingle("select * from reservation_view where code = ?",
                ReservationView.class, code);
    }


}
