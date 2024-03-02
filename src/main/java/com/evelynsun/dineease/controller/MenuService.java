package com.evelynsun.dineease.controller;

import com.evelynsun.dineease.dao.MenuDAO;
import com.evelynsun.dineease.domain.Menu;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @author evelynsun
 */

@RestController
@RequestMapping("/menu")
public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    @PostMapping("/create-menu")
    private void createTable(@RequestBody Menu menu) {
        menuDAO.update("insert into menu (`type`, `name`, ingredients, unit_price, remark) values" +
                        " (?,?,?,?,?)", menu.getType(), menu.getName(),
                menu.getIngredients(), menu.getUnit_price(), menu.getRemark());
    }

    @DeleteMapping("/delete-menu/{menuID}")
    public void deleteMenu(@PathVariable Integer menuID) {
        if (getMenuStatus(menuID).equals("INACTIVE")) {
            menuDAO.update("delete from menu where id = ?", menuID);
        } else {
            throw new RuntimeException("The menu is active and cannot be deleted.");
        }
    }

    @PutMapping("/edit-menu/{menuID}")
    public Menu editTable(@PathVariable Integer menuID,
                          @RequestBody Menu menu) {
        menuDAO.update("update menu set type = ?, name = ? ingredients = ?, unit_price = " +
                        "?, remark = ? where id = ?", menu.getType(), menu.getName(),
                menu.getIngredients(), menu.getUnit_price(), menu.getRemark(), menuID);
        return getMenuByID(menuID);
    }

    @PatchMapping("/activate-menu/{menuID}")
    public Menu activateTable(@PathVariable Integer menuID) {
        menuDAO.update("update menu set status = 'ACTIVE' where id = ?", menuID);
        return getMenuByID(menuID);
    }

    @PatchMapping("/deactivate-menu/{menuID}")
    public Menu deactivateTable(@PathVariable Integer menuID) {
        menuDAO.update("update menu set status = 'INACTIVE' where id = ?", menuID);
        return getMenuByID(menuID);
    }

    @GetMapping("/all-menu")
    public List<Menu> getMenuList() {
        return menuDAO.queryList("select * from menu", Menu.class);
    }

    @GetMapping("/{menuID}")
    public Menu getMenu(@PathVariable Integer menuID) {
        return getMenuByID(menuID);
    }

    public Menu getMenuByID(Integer menuID) {
        return menuDAO.querySingle("select * from menu where id = ?", Menu.class, menuID);
    }

    public String getMenuStatus(Integer menuID) {
        return (String) menuDAO.queryScaler("select status from menu where id = ?", menuID);
    }
}
