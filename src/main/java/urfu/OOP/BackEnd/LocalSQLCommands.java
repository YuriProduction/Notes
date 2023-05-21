package urfu.OOP.BackEnd;

public class LocalSQLCommands {
    public static String SELECT_DATA(java.sql.Date date) {
        return "select * from allNotes where date_of_record = " + "'" + date + "'";
    }

    public static String DELETE_DATA() {
        return "DELETE FROM allNotes WHERE date_of_record = "
                + "?" + " AND " + "Time = " + "?";
    }

    public static String INSERT_DATA(SQLRecord record) {
        //по-хорошему так везде
        if (record instanceof FullRecord) {
            return "insert into allNotes (date_of_record, record, percent, Time) values "
                    + "(?,?,?,?)";
        }
        return null;
    }
}
