package Hospital.GUI;

import Hospital.Core.Appointment;
import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AppointmentAddPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JComboBox<Patient> patientBox;
    private JComboBox<Doctor> doctorBox;
    private JTextField dateField;
    private JTextField timeField;

    public AppointmentAddPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header

        JLabel title = new JLabel("Add Appointment");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);


        // Form

        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Patient:"));

        patientBox = new JComboBox<>();
        formPanel.add(patientBox);


        formPanel.add(new JLabel("Doctor:"));

        doctorBox = new JComboBox<>();
        formPanel.add(doctorBox);


        formPanel.add(new JLabel("Date:"));

        dateField = new JTextField();
        formPanel.add(dateField);


        formPanel.add(new JLabel("Time:"));

        timeField = new JTextField();
        formPanel.add(timeField);


        add(formPanel, BorderLayout.CENTER);


        // Footer

        JPanel footerPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelBtn =
                new JButton("Cancel");

        JButton createBtn =
                new JButton("Create Appointment");

        footerPanel.add(cancelBtn);
        footerPanel.add(createBtn);

        add(footerPanel, BorderLayout.SOUTH);


        // Events

        cancelBtn.addActionListener(e ->
                contentPanel.showAppointments()
        );

        createBtn.addActionListener(e ->
                createAppointment()
        );


        refreshFields();
    }


    public void refreshFields() {

        patientBox.removeAllItems();

        for (Patient patient : hospital.getPatients()) {
            patientBox.addItem(patient);
        }


        doctorBox.removeAllItems();

        for (Doctor doctor : hospital.getDoctors()) {
            doctorBox.addItem(doctor);
        }


        dateField.setText("");
        timeField.setText("");
    }


    private void createAppointment() {

        Patient patient =
                (Patient) patientBox.getSelectedItem();

        Doctor doctor =
                (Doctor) doctorBox.getSelectedItem();


        if (patient == null || doctor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient and doctor."
            );

            return;
        }


        LocalDate date;

        LocalTime time;


        try {

            date =
                    LocalDate.parse(
                            dateField.getText().trim()
                    );

            time =
                    LocalTime.parse(
                            timeField.getText().trim()
                    );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date or time format.\n\n" +
                            "Date example: 2026-08-20\n" +
                            "Time example: 10:30"
            );

            return;
        }


        Appointment appointment =
                hospital.createAppointment(
                        hospital.generateAppointmentId(),
                        patient,
                        doctor,
                        date,
                        time
                );


        if (appointment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    hospital.getLastAppointmentError()
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Appointment created successfully."
        );


        contentPanel.showAppointments();
    }
}