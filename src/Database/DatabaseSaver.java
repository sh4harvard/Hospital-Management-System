package Database;

import Hospital.Core.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSaver {

    private final HospitalSystem hospital;

    public DatabaseSaver(HospitalSystem hospital) {
        this.hospital = hospital;
    }

    public void save() throws SQLException {

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                deleteAll(connection);

                saveWards(connection);
                saveMedicalServices(connection);
                savePatients(connection);
                saveDoctors(connection);
                saveMedicalRecords(connection);
                saveCharges(connection);
                saveAppointments(connection);
                saveIncome(connection);

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    //
    private void deleteAll(Connection connection) throws SQLException {

        connection.createStatement()
                .executeUpdate("DELETE FROM appointments");

        connection.createStatement()
                .executeUpdate("DELETE FROM charges");

        connection.createStatement()
                .executeUpdate("DELETE FROM medical_records");

        connection.createStatement()
                .executeUpdate("DELETE FROM doctors");

        connection.createStatement()
                .executeUpdate("DELETE FROM patients");

        connection.createStatement()
                .executeUpdate("DELETE FROM medical_services");

        connection.createStatement()
                .executeUpdate("DELETE FROM wards");

        connection.createStatement()
                .executeUpdate("DELETE FROM daily_income");
    }
    //

    private void saveWards(Connection connection) throws SQLException {

        String insertSql = "INSERT INTO wards (id, name, capacity) VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(insertSql)) {

            for (Ward ward : hospital.getWards()) {

                statement.setInt(1, ward.getId());
                statement.setString(2, ward.getName());
                statement.setInt(3, ward.getCapacity());

                statement.executeUpdate();
            }
        }
    }

    private void saveMedicalServices(Connection connection) throws SQLException {

        String sql = "INSERT INTO medical_services (id, name, cost) VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (MedicalService service : hospital.getMedicalServices()) {

                statement.setInt(1, service.getId());
                statement.setString(2, service.getName());
                statement.setDouble(3, service.getCost());

                statement.executeUpdate();
            }
        }
    }

    private void savePatients(Connection connection) throws SQLException {

        String sql =
                "INSERT INTO patients " +
                        "(id, name, age, gender, phone_number, ward_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Patient patient : hospital.getPatients()) {

                statement.setInt(1, patient.getId());
                statement.setString(2, patient.getName());
                statement.setInt(3, patient.getAge());
                statement.setString(4, patient.getGender().name());
                statement.setString(5, patient.getPhoneNumber());

                if (patient.getWard() != null) {
                    statement.setInt(6, patient.getWard().getId());
                } else {
                    statement.setNull(6, java.sql.Types.INTEGER);
                }

                statement.executeUpdate();
            }
        }
    }

    private void saveDoctors(Connection connection) throws SQLException {

        String sql =
                "INSERT INTO doctors " +
                        "(id, name, age, gender, phone_number, specialty, " +
                        "ward_id, daily_capacity, shift_start, shift_end ) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Doctor doctor : hospital.getDoctors()) {

                statement.setInt(1, doctor.getId());
                statement.setString(2, doctor.getName());
                statement.setInt(3, doctor.getAge());
                statement.setString(4, doctor.getGender().name());
                statement.setString(5, doctor.getPhoneNumber());
                statement.setString(6, doctor.getSpecialty());
                statement.setInt(8, doctor.getDailyCapacity());
                statement.setString(9, doctor.getShiftStart().toString());
                statement.setString(10, doctor.getShiftEnd().toString());

                if (doctor.getWard() != null) {
                    statement.setInt(7, doctor.getWard().getId());
                } else {
                    statement.setNull(7, java.sql.Types.INTEGER);
                }

                statement.executeUpdate();
            }
        }
    }

    private void saveMedicalRecords(Connection connection) throws SQLException {

        String sql =
                "INSERT INTO medical_records " +
                        "(patient_id, diagnosis, prescription, notes, last_diagnose_date) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Patient patient : hospital.getPatients()) {

                MedicalRecord record = patient.getMedicalRecord();

                if (record == null) {
                    continue;
                }

                statement.setInt(1, patient.getId());
                statement.setString(2, record.getDiagnosis());
                statement.setString(3, record.getPrescription());
                statement.setString(4, record.getNotes());
                statement.setString(5, record.getLastDiagnoseDate().toString());

                statement.executeUpdate();
            }
        }
    }

    private void saveCharges(Connection connection) throws SQLException {

        String sql =
                "INSERT INTO charges " +
                        "(id, patient_id, service_id, pay_status, charge_date) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Patient patient : hospital.getPatients()) {

                for (Charge charge : patient.getBill().getCharges()) {

                    statement.setInt(1, charge.getId());
                    statement.setInt(2, patient.getId());
                    statement.setInt(3, charge.getService().getId());
                    statement.setInt(4, charge.getPayStatus() ? 1 : 0);
                    statement.setString(5, charge.getDate().toString());

                    statement.executeUpdate();
                }
            }
        }
    }

    private void saveAppointments(Connection connection) throws SQLException {

        String sql =
                "INSERT INTO appointments " +
                        "(id, patient_id, doctor_id, app_date, app_time, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Appointment appointment : hospital.getAppointments()) {

                statement.setInt(1, appointment.getId());
                statement.setInt(2, appointment.getPatient().getId());
                statement.setInt(3, appointment.getDoctor().getId());
                statement.setString(4, appointment.getDate().toString());
                statement.setString(5, appointment.getTime().toString());
                statement.setString(6, appointment.getApStatus().name());

                statement.executeUpdate();
            }
        }
    }

    private void saveIncome(Connection connection) throws SQLException {
        // next
    }
}
