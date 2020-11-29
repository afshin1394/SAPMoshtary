package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class SystemMenuModel
{

    private static final String SystemMenu = "SystemMenu";
    private static final String SystemMenuLinkType = "SystemMenuLinkType";
    private static final String SystemPermission = "SystemPermission";
    private static final String COLUMN_ccSystemMenu= "ccSystemMenu";
    private static final String COLUMN_Name = "Name";
    private static final String COLUMN_Parent = "Parent";
    private static final String COLUMN_IconName = "IconName";
    private static final String COLUMN_ccSystemMenuLinkType = "ccSystemMenuLinkType";
    private static final String COLUMN_Link = "Link";
    private static final String COLUMN_TypeName = "TypeName";
    private static final String COLUMN_Sort = "Sort";


    public static String SystemMenu() {
        return SystemMenu;
    }
    public static String SystemMenuLinkType() {
        return SystemMenuLinkType;
    }
    public static String SystemPermission() {
        return SystemPermission;
    }
    public static String COLUMN_ccSystemMenu() {
        return COLUMN_ccSystemMenu;
    }
    public static String COLUMN_Name() {
        return COLUMN_Name;
    }
    public static String COLUMN_Parent() {
        return COLUMN_Parent;
    }
    public static String COLUMN_IconName() {
        return COLUMN_IconName;
    }
    public static String COLUMN_ccSystemMenuLinkType() {
        return COLUMN_ccSystemMenuLinkType;
    }
    public static String COLUMN_Link() {
        return COLUMN_Link;
    }
    public static String COLUMN_TypeName() {
        return COLUMN_TypeName;
    }
    public static String COLUMN_Sort() {
        return COLUMN_Sort;
    }
    public static String ACTIVITY() {
        return ACTIVITY;
    }
    public static String FRAGMENT() {
        return FRAGMENT;
    }
    public static String AlertDialog() {
        return ALERT_DIALOG;
    }
    public static String AlertAbout() {
        return ALERT_ABOUT;
    }





    private int id;
    private String menuName;
    private int parent;
    private String iconName;
    private int linkTypeId;
    private String linkTypeName;
    private String link;
    private int sort;

    private static final String ACTIVITY = "activity";
    private static final String FRAGMENT = "fragment";
    private static final String ALERT_DIALOG = "alertdialog";


    private static final String ALERT_ABOUT = "alert_about";
    private static final String ALERT_EXIT = "alert_exit";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(int linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public String getLinkTypeName() {
        return linkTypeName;
    }

    public void setLinkTypeName(String linkTypeName) {
        this.linkTypeName = linkTypeName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public static String LINK_TYPE_ACTIVITY() { return ACTIVITY; }
    public static String LINK_TYPE_FRAGMENT() {
        return FRAGMENT;
    }
    public static String LINK_TYPE_ALERT_DIALOG() { return ALERT_DIALOG; }


    public static String ALERT_ABOUT() {
        return ALERT_ABOUT;
    }
    public static String ALERT_EXIT() {
        return ALERT_EXIT;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "SystemMenuModel{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", parent=" + parent +
                ", iconName='" + iconName + '\'' +
                ", linkTypeId=" + linkTypeId +
                ", linkTypeName='" + linkTypeName + '\'' +
                ", link='" + link + '\'' +
                ", sort=" + sort +
                '}';
    }
}
