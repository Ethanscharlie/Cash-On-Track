package model;

import java.time.LocalDate;

public class SpendRecord {
    public double cash;
    public LocalDate date;

    public SpendRecord(final double cash, final LocalDate date) {
        this.cash = cash;
        this.date = date;
    }
}
