package urfu.OOP.BackEnd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNotesHandler implements IDataBase {

    private static final String userName = "root";
    private static final String password = "admin";
    private static final String connectionURL = "jdbc:mysql://localhost:3306/notes";
    private final List<SQLRecord> allRecords = new ArrayList<>();

    private void connectToDataBase() throws NullPointerException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            System.out.println("Class not found exception!");
            System.exit(-1);
        }
    }

    private void readRecords(String date) {
        this.connectToDataBase();
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            System.out.println("We're connected");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(LocalSQLCommands.SELECT_DATA(java.sql.Date.valueOf(date)));
            while (resultSet.next()) {
                String record = resultSet.getString(2);
                int is_done = resultSet.getInt(3);
                double percent = resultSet.getDouble(4);
                String time = resultSet.getString(5);
                System.out.println(date + " " + record + " " + is_done
                        + " " + percent + " " + time);
                System.out.println("______________________");
                allRecords.add(new FullRecord(Date.valueOf(date), record, is_done, percent, time));
            }
        } catch (java.sql.SQLException exception) {
            System.out.println("Can't connect with database!");
            System.out.println(exception.getMessage());
            System.exit(-1);
        } catch (NullPointerException exception) {
            System.out.println("Unsuccesful connection to database!");
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(-1);
        }

    }


    @Override
    public List<SQLRecord> getRecords() {
        return null;
    }

    @Override
    public List<SQLRecord> getRecords(String date) {
        this.readRecords(date);
        return allRecords;
    }

    @Override
    public void insertRecord(SQLRecord record) {
        if (record instanceof FullRecord) {
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                System.out.println("We're connected");
                PreparedStatement statement = connection.prepareStatement(LocalSQLCommands.INSERT_DATA(record));
                statement.setDate(1, ((FullRecord) record).Date());
                statement.setString(2, ((FullRecord) record).Record());
                statement.setInt(3, ((FullRecord) record).Is_done());
                statement.setDouble(4, ((FullRecord) record).Percent());
                statement.setString(5, ((FullRecord) record).Time());
                statement.execute();

            } catch (java.sql.SQLException exception) {
                System.out.println(exception.getMessage());
                System.exit(-1);
            }
        }
    }

    @Override
    public void deleteRecord(SQLRecord record) {
        if (record instanceof FullRecord) {
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                System.out.println("We're connected");
                PreparedStatement statement = connection.prepareStatement(LocalSQLCommands.DELETE_DATA());
                statement.setDate(1, ((FullRecord) record).Date());
                statement.setString(2, ((FullRecord) record).Time());
                statement.execute();
            } catch (java.sql.SQLException exception) {
                System.out.println(exception.getMessage());
                System.exit(-1);
            }
        }
    }


    public static void main(String[] args) {
        MySqlNotesHandler handler = new MySqlNotesHandler();
        String date = "2023-05-14";
        SQLRecord record = new FullRecord(java.sql.Date.valueOf(date), "Сделать уроки", 0, 32.5, "19:38");
        handler.deleteRecord(record);
        handler.insertRecord(record);
        List<SQLRecord> recordList = handler.getRecords(date);
        int a = 5;
        System.out.println(a);
    }
}
