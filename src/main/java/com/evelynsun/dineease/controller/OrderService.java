package com.evelynsun.dineease.controller;

import com.evelynsun.dineease.dao.*;
import com.evelynsun.dineease.domain.Order;
import com.evelynsun.dineease.domain.OrderMenu;
import com.evelynsun.dineease.domain.OrderMenuView;
import com.evelynsun.dineease.domain.OrderView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * @author evelynsun
 */
@RestController
@RequestMapping("/order")
public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderMenuDAO orderMenuDAO = new OrderMenuDAO();
    private OrderViewDAO orderViewDAO = new OrderViewDAO();
    private OrderMenuViewDAO orderMenuViewDAO = new OrderMenuViewDAO();
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public Order createOrder(int tableId) {
        String orderCode = UUID.randomUUID().toString();
        orderDAO.update("insert into `order` (code, table_id) values (?, ?)", orderCode, tableId);
        return getOrder(orderCode);
    }

    //can only order one dish at a time
    @PostMapping("/create-order-menu/{orderCode}")
    public OrderMenu createOrderMenu(@PathVariable String orderCode,
                                     @RequestBody OrderMenu orderMenu) {
        String orderMenuCode = UUID.randomUUID().toString();
        orderMenuDAO.update("insert into order_menu (code, order_code, menu_id, quantity, " +
                        "remark) values (?, ?, ?, ?, ?)", orderMenuCode, orderCode,
                orderMenu.getMenu_id(), orderMenu.getQuantity(), orderMenu.getRemark());
        //update order status
        setOrderStatusUpdated(orderCode);
        //update order total price
        updateOrderTotal(orderCode);
        return getOrderMenu(orderMenuCode);
    }

    public void setOrderStatusUpdated(String orderCode) {
        orderDAO.update("update `order` set status = 'UPDATED' where code = ?", orderCode);
    }

    @PatchMapping("/edit-order-menu-quantity/{orderMenuCode}")
    public OrderMenu editMenuQuantity(@PathVariable String orderMenuCode,
                                      @RequestParam Integer quantity) {
        if (quantity >= 1) {
            orderMenuDAO.update("update order_menu set quantity = ? where code = ?", quantity,
                    orderMenuCode);
            //update the price
            updateOrderTotal(getOrderMenuView(orderMenuCode).getOrder_code());
        } else {
            throw new RuntimeException("The quantity must be bigger than zero.");
        }
        return getOrderMenu(orderMenuCode);
    }

    @PatchMapping("/cancel-order-menu/{orderMenuCode}")
    public OrderMenu cancelOrderMenu(@PathVariable String orderMenuCode) {
        orderMenuDAO.update("update order_menu set status = 'CANCELLED' where code = ?",
                orderMenuCode);
        return getOrderMenu(orderMenuCode);
    }

    @PatchMapping("/pay")
    public Order pay(@RequestParam String orderCode, @RequestParam Integer paymentMethod) {
        updatePaymentMethod(paymentMethod, orderCode);
        setOrderStatusPaid(orderCode);
        setTableOpen(getOrder(orderCode).getTable_id());
        return getOrder(orderCode);
    }

    @PatchMapping("/flag-order/{orderCode}")
    public Order flagOrder(@PathVariable String orderCode) {
        orderDAO.update("update `order` set status = 'BAD_DEBT' where code = ?", orderCode);
        setTableOpen(getOrder(orderCode).getTable_id());
        return getOrder(orderCode);
    }

    public void setTableOpen(Integer tableID) {
        diningTableDAO.update("update dining_table set use_status = 'OPEN' where id = ?",
                tableID);
    }

    public void setOrderStatusPaid(String orderCode) {
        orderDAO.update("update `order` set status = 'PAID' where code = ?", orderCode);
    }

    public void updateOrderTotal(String orderCode) {
        double totalPrice = (double) orderMenuViewDAO.queryScaler("select sum(unit_price * " +
                "quantity) from order_menu_view where `status` != 'CANCELLED'");
        orderDAO.update("update `order` set total_price = ? where code = ?", totalPrice, orderCode);
    }

    public void updatePaymentMethod(Integer paymentMethod, String orderCode) {
        orderDAO.update("update `order` set payment_method = ? where code = ?", paymentMethod
                , orderCode);
    }

    public void updateCustomerInfo(String customerName, String customerNum, String orderCode) {
        orderDAO.update("update `order` set customerName = ?, customerNum = ? where code = ?"
                , customerName, customerNum, orderCode);
    }

    public Order getOrder(String orderCode) {
        return orderDAO.querySingle("select * from `order` where code = ?", Order.class,
                orderCode);
    }

    public OrderMenu getOrderMenu(String orderMenuCode) {
        return orderMenuDAO.querySingle("select * from order_menu_view where code = ?",
                OrderMenu.class, orderMenuCode);
    }

    public OrderMenuView getOrderMenuView(String orderMenuCode) {
        return orderMenuViewDAO.querySingle("select * from order_menu_view where code = ?",
                OrderMenuView.class, orderMenuCode);
    }

    @GetMapping("/{orderCode}")
    public OrderView getOrderView(@PathVariable String orderCode) {
        return orderViewDAO.querySingle("select * from order_view where code = ?", OrderView.class,
                orderCode);
    }

    @GetMapping("/active-order/{tableID}")
    public Order getActiveOrder(@PathVariable Integer tableID) {
        return orderDAO.querySingle("select * from `order` where table_id = ? and" +
                " (`status` = 'Created' or `status` = 'updated')", Order.class, tableID);
    }

    @GetMapping("/all-orders")
    public List<OrderView> getOrderViewList() {
        return orderViewDAO.queryList("select * from order_view", OrderView.class);
    }

    @GetMapping("/order-menus/{orderCode}")
    public List<OrderMenuView> getOrderMenuViewList(@PathVariable String orderCode) {
        return orderMenuViewDAO.queryList("select * from `order_menu_view` where order_code = ?",
                OrderMenuView.class, orderCode);
    }


}
