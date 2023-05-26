package urfu.OOP.Front;

import java.sql.Date;

public class Note {
    private String name;
    private int progress;
    private java.sql.Date date;
    private String time;

    Note(String name, int progress, java.sql.Date date, String time) {
        this.name = name;
        this.progress = progress;
        this.time = time;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
