package urfu.OOP.BackEnd;

import java.util.List;

public interface INotesDataBase {

    List<SQLRecord> getRecords(String date);

    public void insertRecord(SQLRecord record);

    public void deleteRecord(SQLRecord record);

    public void updateRecord(SQLRecord record, String updatableRecord);
}
