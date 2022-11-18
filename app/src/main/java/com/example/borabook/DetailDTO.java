package com.example.borabook;

public class DetailDTO {
    int bk_year;
    int bk_month;
    int bk_day;
    String id;
    String bk_iow;
    String bk_group;
    String bk_category;
    int bk_money;
    String bk_memo;

    public int getBk_year() {
        return bk_year;
    }

    public void setBk_year(int bk_year) {
        this.bk_year = bk_year;
    }

    public int getBk_month() {
        return bk_month;
    }

    public void setBk_month(int bk_month) {
        this.bk_month = bk_month;
    }

    public int getBk_day() {
        return bk_day;
    }

    public void setBk_day(int bk_day) {
        this.bk_day = bk_day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBk_iow() {
        return bk_iow;
    }

    public void setBk_iow(String bk_iow) {
        this.bk_iow = bk_iow;
    }

    public String getBk_group() {
        return bk_group;
    }

    public void setBk_group(String bk_group) {
        this.bk_group = bk_group;
    }

    public String getBk_category() {
        return bk_category;
    }

    public void setBk_category(String bk_category) {
        this.bk_category = bk_category;
    }

    public int getBk_money() {
        return bk_money;
    }

    public void setBk_money(int bk_money) {
        this.bk_money = bk_money;
    }

    public String getBk_memo() {
        return bk_memo;
    }

    public void setBk_memo(String bk_memo) {
        this.bk_memo = bk_memo;
    }

    @Override
    public String toString() {
        return "DetailDTO{" +
                "bk_year=" + bk_year +
                ", bk_month=" + bk_month +
                ", bk_day=" + bk_day +
                ", id='" + id + '\'' +
                ", bk_iow='" + bk_iow + '\'' +
                ", bk_group='" + bk_group + '\'' +
                ", bk_category='" + bk_category + '\'' +
                ", bk_money=" + bk_money +
                ", bk_memo='" + bk_memo + '\'' +
                '}';
    }
}
