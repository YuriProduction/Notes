package urfu.OOP.BackEnd;

import java.sql.*;
import java.util.Calendar;
public class MySqlConnector implements IDataBase {

    private final LocalSQLCommands sqlCommands = new LocalSQLCommands();

    @Override
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
            while (resultSet.next())
            {
                String record = resultSet.getString(2);
                System.out.println(record);
            }

        } catch (java.sql.SQLException exception) {
            System.out.println("Can't connect with database!");
            System.out.println(exception.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MySqlConnector().connectToDataBase();
    }
}
