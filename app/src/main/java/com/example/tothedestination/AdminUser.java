package com.example.tothedestination;

public class AdminUser extends User{


    private boolean canEditAttraction;
    private boolean canDeleteAttraction;
    private boolean canAddAttraction;
    private boolean canEditFlyList;
    private boolean canDeleteFlyList;
    private boolean canAddFlyList;

    //עולה בונה ריקה כדי שנוכל להתשמש בfirebase
    public AdminUser()
    {

    }
    //יצירת פעולה בונה והכנסת הנתונים שהיא מקבלת לתכונותיה וגם קבלת הנתונים של המחלקה User מכיוון שהמחלקה AdminUser יורשת מUser את כל תכונותיה ומוסיפה לה תכונות אחרות של ניהול שיש רק לadmin
    public AdminUser(String firstName, String lastName, String password, String mail, boolean canEditAttraction, boolean canDeleteAttraction, boolean canAddAttraction, boolean canEditFlyList, boolean canDeleteFlyList, boolean canAddFlyList)  {
        super(firstName, lastName, password, mail);
        this.canEditAttraction = canEditAttraction;
        this.canDeleteAttraction = canDeleteAttraction;
        this.canAddAttraction = canAddAttraction;
        this.canEditFlyList = canEditFlyList;
        this.canDeleteFlyList = canDeleteFlyList;
        this.canAddFlyList = canAddFlyList;
    }

    //פעולות get set
    public boolean isCanEditAttraction() {
        return canEditAttraction;
    }

    public void setCanEditAttraction(boolean canEditAttraction) {
        this.canEditAttraction = canEditAttraction;
    }

    public boolean isCanDeleteAttraction() {
        return canDeleteAttraction;
    }

    public void setCanDeleteAttraction(boolean canDeleteAttraction) {
        this.canDeleteAttraction = canDeleteAttraction;
    }

    public boolean isCanAddAttraction() {
        return canAddAttraction;
    }

    public void setCanAddAttraction(boolean canAddAttraction) {
        this.canAddAttraction = canAddAttraction;
    }

    public boolean isCanEditFlyList() {
        return canEditFlyList;
    }

    public void setCanEditFlyList(boolean canEditFlyList) {
        this.canEditFlyList = canEditFlyList;
    }

    public boolean isCanDeleteFlyList() {
        return canDeleteFlyList;
    }

    public void setCanDeleteFlyList(boolean canDeleteFlyList) {
        this.canDeleteFlyList = canDeleteFlyList;
    }

    public boolean isCanAddFlyList() {
        return canAddFlyList;
    }

    public void setCanAddFlyList(boolean canAddFlyList) {
        this.canAddFlyList = canAddFlyList;
    }
}
