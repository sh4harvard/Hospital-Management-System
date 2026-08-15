package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        // Note: Try Catch closes the DB if error or after complete
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS wards (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "capacity INTEGER NOT NULL" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS doctors (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "age INTEGER NOT NULL, " +
                    "gender TEXT NOT NULL, " +
                    "phone_number TEXT, " +
                    "specialty TEXT NOT NULL, " +
                    "ward_id INTEGER, " +
                    "daily_capacity INTEGER NOT NULL, " +
                    "shift_start TEXT NOT NULL, " +
                    "shift_end TEXT NOT NULL, " +
                    "FOREIGN KEY (ward_id) REFERENCES wards(id)" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS patients (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "age INTEGER NOT NULL, " +
                    "gender TEXT NOT NULL, " +
                    "phone_number TEXT, " +
                    "ward_id INTEGER, " +
                    "FOREIGN KEY (ward_id) REFERENCES wards(id)" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS medical_records(" +
                    "patient_id INTEGER PRIMARY KEY," +
                    "diagnosis TEXT," +
                    "prescription TEXT," +
                    "notes TEXT," +
                    "last_diagnose_date TEXT, " +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id)" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS medical_services (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "cost REAL NOT NULL" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS charges (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "patient_id INTEGER NOT NULL, " +
                    "service_id INTEGER NOT NULL, " +
                    "pay_status INTEGER NOT NULL DEFAULT 0, " +
                    "charge_date TEXT NOT NULL," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id), " +
                    "FOREIGN KEY (service_id) REFERENCES medical_services(id)" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "patient_id INTEGER NOT NULL, " +
                    "doctor_id INTEGER NOT NULL, " +
                    "app_date TEXT NOT NULL, " +
                    "app_time TEXT NOT NULL, " +
                    "status TEXT NOT NULL, " +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id), " +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id)" +
                    ")"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS hospital_income (" +
                    "id INTEGER PRIMARY KEY, " +
                    "income_type TEXT NOT NULL," +
                    "income_id_prop INTEGER NOT NULL," +
                    "name TEXT NOT NULL, " +
                    "amount REAL NOT NULL," +
                    "income_date TEXT NOT NULL" +
                    ")"
            );

            System.out.println("Database initialized!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
