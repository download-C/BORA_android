package com.example.borabook;

public class DetailDTO {
    private BookDTO book;
    private int bk_day;
    private String bk_iow;
    private String bk_group;
    private String bk_category;
    private String bk_money;
    private String bk_memo;

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public int getBk_day() {
        return bk_day;
    }

    public void setBk_day(int bk_day) {
        this.bk_day = bk_day;
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

    public String getBk_money() {
        return bk_money;
    }

    public void setBk_money(String bk_money) {
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
        return  '{'+
                "\"book\":" + book +
                ", \"bk_day\":" + bk_day +
                ", \"bk_iow\":\"" + bk_iow + '\"' +
                ", \"bk_group\":\"" + bk_group + '\"' +
                ", \"bk_category\":\"" + bk_category + '\"' +
                ", \"bk_money\":" + bk_money +
                ", \"bk_memo\":\"" + bk_memo + '\"' +
                '}';
    }
}
