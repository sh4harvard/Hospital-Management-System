package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import Hospital.Core.Patient;
import Hospital.Core.Ward;
import Hospital.Core.enums.Gender;
import java.util.ArrayList;

public class PatientDAO {

    public boolean addPatient(Patient patient) {

        String sql =
            "INSERT INTO patients " +
                "(id, name, age, gender, phone_number, ward_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getName());
            statement.setInt(3, patient.getAge());
            statement.setString(4, patient.getGender().toString());
            statement.setString(5, patient.getPhoneNumber());

            if (patient.getWard() != null) {
                statement.setInt(6, patient.getWard().getId());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Patient getPatient(String id) {

        String sql =
            "SELECT * FROM patients WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                int patientId = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                Gender gender = Gender.valueOf(result.getString("gender"));
                String phoneNumber = result.getString("phone_number");

                Patient patient = new Patient(
                    patientId,
                    name,
                    age,
                    gender,
                    phoneNumber
                );

                int wardId = result.getInt("ward_id");

                if (!result.wasNull()) {
                    WardDAO wardDAO = new WardDAO();
                    Ward ward = wardDAO.getWard(wardId);

                    patient.setWard(ward);
                }

                return patient;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Patient> getAllPatients() {

        ArrayList<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patients";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                int patientId = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                Gender gender = Gender.valueOf(result.getString("gender"));
                String phoneNumber = result.getString("phone_number");

                Patient patient = new Patient(
                    patientId,
                    name,
                    age,
                    gender,
                    phoneNumber
                );

                int wardId = result.getInt("ward_id");

                if (!result.wasNull()) {
                    WardDAO wardDAO = new WardDAO();
                    Ward ward = wardDAO.getWard(wardId);

                    patient.setWard(ward);
                }

                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

}
