package com.example.borabook;

public class BookDTO {
    private int bk_num;
    private int bk_year;
    private int bk_month;
    private int bk_budget;

    public void setBk_num(int bk_num) {
        this.bk_num = bk_num;
    }

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

    public int getBk_budget() {
        return bk_budget;
    }

    public void setBk_budget(int bk_budget) {
        this.bk_budget = bk_budget;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bk_num=" + bk_num +
                ", bk_year=" + bk_year +
                ", bk_month=" + bk_month +
                ", bk_budget=" + bk_budget +
                '}';
    }
}
