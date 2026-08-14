package Database;

import Hospital.Core.Ward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WardDAO {

    public boolean addWard(Ward ward) {

        String sql =
            "INSERT INTO wards (name, capacity) " +
                    "VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     // for the auto increment id
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, ward.getName());
            statement.setInt(2, ward.getCapacity());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
            //    ward.setId(keys.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public Ward getWard(int id) {

        String sql =
            "SELECT * FROM wards WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                int wardId = result.getInt("id");
                String name = result.getString("name");
                int capacity = result.getInt("capacity");

                return new Ward(wardId, name, capacity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // 3. Get all wards
    public ArrayList<Ward> getAllWards() {

        ArrayList<Ward> wards = new ArrayList<>();

        String sql =
                "SELECT * FROM wards";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                int id = result.getInt("id");
                String name = result.getString("name");
                int capacity = result.getInt("capacity");

                Ward ward = new Ward(id, name, capacity);

                wards.add(ward);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wards;
    }
}