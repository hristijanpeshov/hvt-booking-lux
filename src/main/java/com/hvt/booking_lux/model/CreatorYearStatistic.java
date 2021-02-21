package com.hvt.booking_lux.model;

public class CreatorYearStatistic {

    private int month;
    private int num;

    public CreatorYearStatistic(int month, int num) {
        this.month = month;
        this.num = num;
    }

    public CreatorYearStatistic() {
    }

    public int getMonth() {
        return month;
    }

    public int getNum() {
        return num;
    }
}
