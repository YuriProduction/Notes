package urfu.OOP.BackEnd;

public class LocalSQLCommands {
    public static String SELECT_DATA(java.sql.Date date) {
        return "select * from allNotes where date_of_record = " + "'" + date + "'";
    }
}
