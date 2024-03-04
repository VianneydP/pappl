/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.controllers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
public class Menu {

    private static List<Menu> menuList;
    private Integer menuId;
    private String menuTitle;
    private String menuCode;

    private Menu parentId;

    private Collection<Menu> menuCollection;

    public Menu(Integer menuId) {
        this.menuId = menuId;
        this.parentId = null;
        this.menuCollection = new LinkedList<>();
    }

    public Menu(Integer menuId, String menuTitle, String menuCode) {
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.menuCode = menuCode;
        this.parentId = null;
        this.menuCollection = new LinkedList<>();
    }

    public Menu(Integer menuId, String menuTitle, String menuCode, Menu parentId) {
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.menuCode = menuCode;
        this.parentId = parentId;
        this.menuCollection = new LinkedList<>();
        parentId.menuCollection.add(this);
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public Menu getParentId() {
        return parentId;
    }

    public void setParentId(Menu parentId) {
        this.parentId = parentId;
    }

    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    public static void createMenuList() {
        menuList = new LinkedList<>();
    }
    public static void addMenu(Menu menu) {
        menuList.add(menu);
    }
    public static List<Menu> getMenuList() {
        return menuList;
    }

}
