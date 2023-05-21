package urfu.OOP.BackEnd;

import java.sql.Date;

public class AllNotesTableRecord extends SQLRecord {
    private final java.sql.Date date;
    private final String record;
    private final int is_done;

    private final double percent;

    private final String time;

    public Date Date() {
        return date;
    }

    public String Record() {
        return record;
    }

    public int Is_done() {
        return is_done;
    }

    public double Percent() {
        return percent;
    }

    public String Time() {
        return time;
    }

    public AllNotesTableRecord(java.sql.Date date, String record,
                               int is_done, double percent, String time) {
        this.date = date;
        this.record = record;
        this.is_done = is_done;
        this.percent = percent;
        this.time = time;
    }
}
