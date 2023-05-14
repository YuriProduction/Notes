package urfu.OOP.BackEnd;

import java.util.List;

public interface IDataBase {
    public List<SQLRecord> getRecords() throws NullPointerException;
}
