package Database;

import Hospital.Core.*;
import Hospital.Core.enums.AppointmentStatus;
import Hospital.Core.enums.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DatabaseLoader {

    private final HospitalSystem hospital;

    public DatabaseLoader(HospitalSystem hospital) {
        this.hospital = hospital;
    }

    public void load() throws SQLException {

        hospital.getWards().clear();
        hospital.getMedicalServices().clear();
        hospital.getPatients().clear();
        hospital.getDoctors().clear();
        hospital.getAppointments().clear();
        hospital.getHospitalIncomes().clear();

        try (Connection connection = DatabaseConnection.getConnection()) {

            loadWards(connection);
            loadMedicalServices(connection);
            loadPatients(connection);
            loadDoctors(connection);
            loadMedicalRecords(connection);
            loadCharges(connection);
            loadAppointments(connection);
            loadIncome(connection);

            hospital.updateNextIds();
        }
    }

    private void loadWards(Connection connection) throws SQLException {
        String sql = "SELECT id, name, capacity FROM wards";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int capacity = resultSet.getInt("capacity");

                Ward ward = new Ward(id, name, capacity);

                hospital.getWards().add(ward);
            }
        }
    }

    private void loadMedicalServices(Connection connection) throws SQLException {
        String sql = "SELECT id, name, cost FROM medical_services";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double cost = resultSet.getDouble("cost");

                MedicalService service = new MedicalService(id, name, cost);

                hospital.getMedicalServices().add(service);
            }
        }
    }

    private void loadPatients(Connection connection) throws SQLException {
        String sql = "SELECT id, name, age, gender, phone_number, ward_id FROM patients";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                Gender gender = Gender.valueOf(resultSet.getString("gender"));
                String phoneNumber = resultSet.getString("phone_number");

                Patient patient = new Patient(id, name, age, gender, phoneNumber);

                hospital.getPatients().add(patient);

                int wardId = resultSet.getInt("ward_id");
                boolean hasWard = !resultSet.wasNull();

                if (hasWard) {
                    Ward ward = hospital.findWardById(wardId);

                    if (ward != null) {
                        ward.addPatient(patient);
                    }
                }
            }
        }
    }

    private void loadDoctors(Connection connection) throws SQLException {
        String sql = "SELECT id, name, age, gender, phone_number, specialty, " +
                "ward_id, daily_capacity, shift_start, shift_end " +
                "FROM doctors";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                Gender gender = Gender.valueOf(resultSet.getString("gender"));
                String phoneNumber = resultSet.getString("phone_number");
                String specialty = resultSet.getString("specialty");
                int dailyCapacity = resultSet.getInt("daily_capacity");
                String shiftStartText = resultSet.getString("shift_start");
                String shiftEndText = resultSet.getString("shift_end");

                // store in time
                LocalTime shiftStart = LocalTime.parse(shiftStartText);
                LocalTime shiftEnd = LocalTime.parse(shiftEndText);

                Doctor doctor = new Doctor(id, name, age, gender, phoneNumber, specialty, null, dailyCapacity, shiftStart, shiftEnd);

                hospital.getDoctors().add(doctor);


                int wardId = resultSet.getInt("ward_id");
                boolean hasWard = !resultSet.wasNull();

                if (hasWard) {
                    Ward ward = hospital.findWardById(wardId);

                    if (ward != null) {
                        ward.addDoctor(doctor);
                    }
                }
            }
        }
    }

    private void loadMedicalRecords(Connection connection) throws SQLException {
        String sql = "SELECT patient_id, diagnosis, prescription, notes, last_diagnose_date " +
                "FROM medical_records";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("patient_id");
                String diagnosis = resultSet.getString("diagnosis");
                String prescription = resultSet.getString("prescription");
                String notes = resultSet.getString("notes");
                String dateText =
                        resultSet.getString("last_diagnose_date");

                LocalDate lastDate = null;

                if (dateText != null) {
                    lastDate = LocalDate.parse(dateText);
                }

                Patient patient = hospital.findPatientById(id);

                if (patient == null) {
                    System.out.println(
                            "Warning: Medical record refers to missing patient " + id
                    );
                    continue;
                }

                MedicalRecord record = new MedicalRecord(id);
                record.setMRecord(
                        diagnosis,
                        prescription,
                        notes,
                        lastDate
                );

                patient.setMedicalRecord(record);
            }
        }
    }

    private void loadCharges(Connection connection) throws SQLException {

        String sql =
                "SELECT id, patient_id, service_id, pay_status, charge_date " +
                        "FROM charges";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patient_id");
                int serviceId = resultSet.getInt("service_id");

                boolean payStatus =
                        resultSet.getInt("pay_status") == 1;

                LocalDate date =
                        LocalDate.parse(
                                resultSet.getString("charge_date")
                        );

                Patient patient =
                        hospital.findPatientById(patientId);

                MedicalService service =
                        hospital.findMedicalServicebyId(serviceId);

                if (patient == null) {
                    System.out.println(
                            "Warning: Charge " + id +
                                    " refers to missing patient " +
                                    patientId
                    );
                    continue;
                }

                if (service == null) {
                    System.out.println(
                            "Warning: Charge " + id +
                                    " refers to missing medical service " +
                                    serviceId
                    );
                    continue;
                }

                Charge charge =
                        new Charge(
                                id,
                                service,
                                date
                        );

                charge.setPayStatus(payStatus);

                patient.getBill().addCharge(charge);
            }
        }
    }

    private void loadAppointments(Connection connection) throws SQLException {
        String sql = "SELECT id, patient_id, doctor_id, app_date, app_time, status " +
                "FROM appointments";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");
                LocalDate date = LocalDate.parse(resultSet.getString("app_date"));
                LocalTime time = LocalTime.parse(resultSet.getString("app_time"));
                AppointmentStatus status = AppointmentStatus.valueOf(resultSet.getString("status"));

                Patient patient = hospital.findPatientById(patientId);
                Doctor doctor = hospital.findDoctorbyId(doctorId);

                if (patient == null || doctor == null) {
                    System.out.println(
                            "Warning: Appointment " + id +
                                    " refers to a missing patient or doctor."
                    );
                    continue;
                }

                Appointment appointment = new Appointment(id, patient, doctor, date, time);
                appointment.setApStatus(status);

                hospital.getAppointments().add(appointment);
                patient.getAppointments().add(appointment);
                doctor.getAppointments().add(appointment);

            }
        }

        hospital.getAppointments().sort(null);

        for (Patient patient : hospital.getPatients()) {
            patient.getAppointments().sort(null);
        }

        for (Doctor doctor : hospital.getDoctors()) {
            doctor.getAppointments().sort(null);
        }

    }

    private void loadIncome(Connection connection) throws SQLException {
        String sql = "SELECT id, income_type, income_id_prop, name, amount, income_date " +
                "FROM hospital_income";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String type = resultSet.getString("income_type");
                int propID = resultSet.getInt("income_id_prop");
                String name = resultSet.getString("name");
                double amount = resultSet.getDouble("amount");
                LocalDate date = LocalDate.parse(resultSet.getString("income_date"));

                if (type.equals("MEDICAL_SERVICE")) {
                    MedicalService medicalService = hospital.findMedicalServicebyName(name);
                    Patient patient = hospital.findPatientById(propID);
                    HospitalIncome income = new IncomeMedicalService(medicalService, patient);
                    income.setAmount(amount);
                    income.setDate(date);
                    hospital.getHospitalIncomes().add(income);
                }
                else if (type.equals("WARD_BONUS")) {
                    Ward ward = hospital.findWardById(propID);
                    HospitalIncome wardBonus = new WardBonus(ward);
                    wardBonus.setAmount(amount);
                    wardBonus.setDate(date);
                    hospital.getHospitalIncomes().add(wardBonus);
                }

            }
        }
    }
}
