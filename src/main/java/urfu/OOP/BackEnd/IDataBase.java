package urfu.OOP.BackEnd;

import java.sql.SQLException;
import java.util.List;

public interface IDataBase {
    public List<SQLRecord> getRecords();

    List<SQLRecord> getRecords(String date);

    public void insertRecord(SQLRecord record);

    public void deleteRecord(SQLRecord record);
}
