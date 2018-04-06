package org.pceindicator.com.model;

/**
 * Created by ABHIJAY on 3/17/2018.
 */

public class UserModel {
    public String division;
    public String branch;
    public String year;

    public UserModel(String uid){
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String uid;

    public UserModel(String division,String branch,String year){
        this.branch = branch;
        this.division = division;
        this.year = year;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
