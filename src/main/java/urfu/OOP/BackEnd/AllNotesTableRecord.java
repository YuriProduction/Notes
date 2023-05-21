package urfu.OOP.BackEnd;

import java.sql.Date;

public class AllNotesTableRecord extends SQLRecord {
    private final java.sql.Date date;
    private final String record;

    private final double percent;

    private final String time;

    public Date Date() {
        return date;
    }

    public String Record() {
        return record;
    }

    public double Percent() {
        return percent;
    }

    public String Time() {
        return time;
    }

    public AllNotesTableRecord(java.sql.Date date, String record,
                               double percent, String time) {
        this.date = date;
        this.record = record;
        this.percent = percent;
        this.time = time;
    }
}
