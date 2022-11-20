package com.example.borabook;

public class DetailDTO {

    private String bk_iow;
    private String bk_group;
    private String bk_category;
    private int bk_money;
    private String bk_memo;

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
                "bk_iow='" + bk_iow + '\'' +
                ", bk_group='" + bk_group + '\'' +
                ", bk_category='" + bk_category + '\'' +
                ", bk_money=" + bk_money +
                ", bk_memo='" + bk_memo + '\'' +
                '}';
    }
}
