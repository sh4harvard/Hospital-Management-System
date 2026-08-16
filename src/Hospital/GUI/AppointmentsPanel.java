package Hospital.GUI;

import Hospital.Core.Appointment;
import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentsPanel extends JPanel {

    private Patient patient;
    private final HospitalSystem hospital;

    private DefaultTableModel tableModel;
    private JTable appointmentsTable;

    public AppointmentsPanel(HospitalSystem hospital){

        this.hospital = hospital;

        setLayout(new BorderLayout(0, 15));
        setBackground(AppColors.BACKGROUND);

        JLabel title = new JLabel("Appointments");
        UIStyle.styleTitle(title);

        add(title, BorderLayout.NORTH);


        String[] titles = {
                "ID",
                "Doctor",
                "Date",
                "Time",
                "Status"
        };

        tableModel = new DefaultTableModel(titles, 0);
        appointmentsTable = new JTable(tableModel);
        UIStyle.styleTable(appointmentsTable);

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonFooterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonFooterPanel.setBackground(AppColors.BACKGROUND);

        JButton newAppointmentButton = new JButton("New Appointment");
        UIStyle.styleButton(newAppointmentButton);

        buttonFooterPanel.add(newAppointmentButton);
        newAppointmentButton.addActionListener(e -> createNewAppointment());

        add(buttonFooterPanel, BorderLayout.SOUTH);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        updateAppointments();
    }

    private void updateAppointments() {

        tableModel.setRowCount(0);

        if (patient == null) {
            return;
        }

        for (Appointment appointment : patient.getAppointments()) {

            tableModel.addRow(new Object[] {
                    appointment.getId(),
                    appointment.getDoctor().getName(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getApStatus()
            });
        }

    }

    private void createNewAppointment() {

        if (patient == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient first."
            );
            return;
        }

        if (hospital.getDoctors().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no doctors available."
            );
            return;
        }

        JComboBox<Doctor> doctorComboBox =
                new JComboBox<>(
                        hospital.getDoctors().toArray(new Doctor[0])
                );

        JTextField dateField =
                new JTextField();

        JTextField timeField =
                new JTextField();

        JPanel panel =
                new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Doctor:"));
        panel.add(doctorComboBox);

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);

        panel.add(new JLabel("Time (HH:MM):"));
        panel.add(timeField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "New Appointment",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            Doctor doctor =
                    (Doctor) doctorComboBox.getSelectedItem();

            LocalDate date =
                    LocalDate.parse(
                            dateField.getText().trim()
                    );

            LocalTime time =
                    LocalTime.parse(
                            timeField.getText().trim()
                    );

            int appointmentId =
                    hospital.generateAppointmentId();

            Appointment appointment =
                    hospital.createAppointment(
                            appointmentId,
                            patient,
                            doctor,
                            date,
                            time
                    );

            if (appointment == null) {

                JOptionPane.showMessageDialog(
                        this,
                        hospital.getLastAppointmentError(),
                        "Appointment Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            updateAppointments();

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment created successfully."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date or time.\n" +
                            "Use YYYY-MM-DD and HH:MM.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
