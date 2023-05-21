package urfu.OOP.BackEnd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNotesHandler implements INotesDataBase {

    private static final String userName = "root";
    private static final String password = "admin";
    private static final String connectionURL = "jdbc:mysql://localhost:3306/notes";
    private List<SQLRecord> allRecords;

    private void connectToDataBase() throws NullPointerException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            System.out.println("Class not found exception!");
            System.exit(-1);
        }
    }

    public void readRecords(String date) {
        this.connectToDataBase();
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            System.out.println("We're connected");
            Statement statement = connection.createStatement();
            allRecords = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(LocalSQLCommands.SELECT_DATA(java.sql.Date.valueOf(date)));
            while (resultSet.next()) {
                String record = resultSet.getString(2);
                double percent = resultSet.getDouble(3);
                String time = resultSet.getString(4);
                System.out.println(date + " " + record + " " + " " + percent + " " + time);
                System.out.println("______________________");

                allRecords.add(new AllNotesTableRecord(Date.valueOf(date), record, percent, time));
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
    public List<SQLRecord> getRecords(String date) {
        this.readRecords(date);
        return allRecords;
    }

    @Override
    public void insertRecord(SQLRecord record) {
        if (record instanceof AllNotesTableRecord) {
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                System.out.println("We're connected");
                PreparedStatement statement = connection.prepareStatement(LocalSQLCommands.INSERT_DATA(record));
                statement.setDate(1, ((AllNotesTableRecord) record).Date());
                statement.setString(2, ((AllNotesTableRecord) record).Record());
                statement.setDouble(3, ((AllNotesTableRecord) record).Percent());
                statement.setString(4, ((AllNotesTableRecord) record).Time());
                statement.execute();

            } catch (java.sql.SQLException exception) {
                System.out.println(exception.getMessage());
                System.exit(-1);
            }
        }
    }

    @Override
    public void deleteRecord(SQLRecord record) {
        if (record instanceof AllNotesTableRecord) {
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                System.out.println("We're connected");
                PreparedStatement statement = connection.prepareStatement(LocalSQLCommands.DELETE_DATA());
                statement.setDate(1, ((AllNotesTableRecord) record).Date());
                statement.setString(2, ((AllNotesTableRecord) record).Time());
                statement.setString(3, ((AllNotesTableRecord) record).Record());
                statement.execute();
            } catch (java.sql.SQLException exception) {
                System.out.println(exception.getMessage());
                System.exit(-1);
            }
        }
    }

    @Override
    public void updateRecord(SQLRecord record, String updatableRecord, int newPercents) {
        if (record instanceof AllNotesTableRecord) {
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                System.out.println("We're connected");
                PreparedStatement statement = connection.prepareStatement(LocalSQLCommands.UPDATE_DATA(record));
                statement.setString(1, updatableRecord);
                statement.setInt(2, newPercents);
                statement.setString(3, ((AllNotesTableRecord) record).Record());
                statement.setDate(4, ((AllNotesTableRecord) record).Date());
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
        SQLRecord record = new AllNotesTableRecord(java.sql.Date.valueOf(date), "Сделать уроки", 32.5, "19:38");
        handler.deleteRecord(record);
        handler.insertRecord(record);
        List<SQLRecord> recordList = handler.getRecords(date);
        int a = 5;
        System.out.println(a);
    }
}


