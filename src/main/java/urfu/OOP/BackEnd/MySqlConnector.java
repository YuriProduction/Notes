package urfu.OOP.BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlConnector implements IDataBase {

    private final List<SQLRecord> allRecords = new ArrayList<>();

    public void connectToDataBase() {
        String username = "root";
        String password = "admin";
        String connectionURL = "jdbc:mysql://localhost:3306/notes";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            System.out.println("Class not found exception!");
            System.exit(0);
        }
        try (Connection connection = DriverManager.getConnection(connectionURL, username, password)) {
            System.out.println("We're connected");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(LocalSQLCommands.SELECT_DATA(java.sql.Date.valueOf("2023-05-14")));
            while (resultSet.next()) {
                java.sql.Date date = resultSet.getDate(1);
                String record = resultSet.getString(2);
                int is_done = resultSet.getInt(3);
                double percent = resultSet.getDouble(4);
                String time = resultSet.getString(5);
                System.out.println(date + " " + record + " " + is_done
                        + " " + percent + " " + time);
                System.out.println("______________________");
                allRecords.add(new FullRecord(date, record, is_done, percent, time));
            }

        } catch (java.sql.SQLException exception) {
            System.out.println("Can't connect with database!");
            System.out.println(exception.getMessage());
            System.exit(0);
        }
    }

    @Override
    public List<SQLRecord> getRecords() {
        return allRecords;
    }

    public static void main(String[] args) {
        MySqlConnector connector = new MySqlConnector();
        connector.connectToDataBase();
        List<SQLRecord> recordList = connector.getRecords();
    }
}
