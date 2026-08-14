package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(
                    "SELECT name FROM sqlite_master " +
                            "WHERE type = 'table'"
            );

            System.out.println("\nTables in database:");

            while (result.next()) {
                System.out.println(result.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}