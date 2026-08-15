package Hospital.GUI;

import Hospital.Core.Appointment;
import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;

public class DoctorDetailPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private Doctor doctor;

    private JLabel doctorID;
    private JLabel nameDoctor;

    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel phoneLabel;

    private JLabel specialtyLabel;
    private JLabel wardLabel;
    private JLabel shiftLabel;
    private JLabel capacityLabel;

    private JTable appointmentsTable;

    public DoctorDetailPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JButton backToBtn = new JButton("<- Doctors");

        doctorID = new JLabel();
        doctorID.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        headerPanel.add(backToBtn, BorderLayout.WEST);
        headerPanel.add(doctorID, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main Body
        JPanel mainBodyPanel =
                new JPanel(new BorderLayout());

        JPanel infoPanel =
                new JPanel(new BorderLayout());

        // Identity
        JPanel identityPanel =
                new JPanel(new GridLayout(0, 1));

        nameDoctor = new JLabel();
        nameDoctor.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        identityPanel.add(nameDoctor);

        infoPanel.add(
                identityPanel,
                BorderLayout.NORTH
        );

        // Personal + Professional
        JPanel infoProfessPersonalPanel =
                new JPanel(new GridLayout(1, 2));

        // Personal
        JPanel personalPanel =
                new JPanel(new BorderLayout());

        personalPanel.add(
                new JLabel("Personal"),
                BorderLayout.NORTH
        );

        JPanel personalInfoPanel =
                new JPanel(new GridLayout(3, 2));

        personalInfoPanel.add(new JLabel("Age:"));
        ageLabel = new JLabel();
        personalInfoPanel.add(ageLabel);

        personalInfoPanel.add(new JLabel("Gender:"));
        genderLabel = new JLabel();
        personalInfoPanel.add(genderLabel);

        personalInfoPanel.add(new JLabel("Phone:"));
        phoneLabel = new JLabel();
        personalInfoPanel.add(phoneLabel);

        personalPanel.add(
                personalInfoPanel,
                BorderLayout.CENTER
        );

        infoProfessPersonalPanel.add(personalPanel);

        // Professional
        JPanel professionalPanel =
                new JPanel(new BorderLayout());

        professionalPanel.add(
                new JLabel("Professional"),
                BorderLayout.NORTH
        );

        JPanel professionalInfoPanel =
                new JPanel(new GridLayout(4, 2));

        professionalInfoPanel.add(
                new JLabel("Specialty:")
        );

        specialtyLabel = new JLabel();
        professionalInfoPanel.add(specialtyLabel);

        professionalInfoPanel.add(
                new JLabel("Ward:")
        );

        wardLabel = new JLabel();
        professionalInfoPanel.add(wardLabel);

        professionalInfoPanel.add(
                new JLabel("Shift:")
        );

        shiftLabel = new JLabel();
        professionalInfoPanel.add(shiftLabel);

        professionalInfoPanel.add(
                new JLabel("Capacity:")
        );

        capacityLabel = new JLabel();
        professionalInfoPanel.add(capacityLabel);

        professionalPanel.add(
                professionalInfoPanel,
                BorderLayout.CENTER
        );

        infoProfessPersonalPanel.add(
                professionalPanel
        );

        infoPanel.add(
                infoProfessPersonalPanel,
                BorderLayout.CENTER
        );

        mainBodyPanel.add(
                infoPanel,
                BorderLayout.NORTH
        );

        // Appointments
        JPanel appointmentPanel =
                new JPanel(new BorderLayout());

        appointmentPanel.add(
                new JLabel("Appointments"),
                BorderLayout.NORTH
        );

        String[] titles = {
                "Date",
                "Time",
                "Patient",
                "Status"
        };

        appointmentsTable =
                new JTable(
                        new Object[0][4],
                        titles
                );

        appointmentPanel.add(
                new JScrollPane(appointmentsTable),
                BorderLayout.CENTER
        );

        mainBodyPanel.add(
                appointmentPanel,
                BorderLayout.CENTER
        );

        add(
                mainBodyPanel,
                BorderLayout.CENTER
        );


        backToBtn.addActionListener(e ->
                contentPanel.showDoctors()
        );
    }

    public void setDoctor(Doctor doctor) {

        this.doctor = doctor;

        doctorID.setText(
                "Doctor #" + doctor.getId()
        );

        nameDoctor.setText(
                doctor.getName()
        );

        ageLabel.setText(
                String.valueOf(doctor.getAge())
        );

        genderLabel.setText(
                doctor.getGender().toString()
        );

        phoneLabel.setText(
                doctor.getPhoneNumber()
        );

        specialtyLabel.setText(
                doctor.getSpecialty()
        );

        if (doctor.getWard() != null) {
            wardLabel.setText(
                    doctor.getWard().getName()
            );
        } else {
            wardLabel.setText("None");
        }

        shiftLabel.setText(
                doctor.getShiftStart()
                        + " - "
                        + doctor.getShiftEnd()
        );

        capacityLabel.setText(
                doctor.getDailyCapacity()
                        + "/day"
        );

        refreshAppointments();
    }

    private void refreshAppointments() {

        String[] titles = {
                "Date",
                "Time",
                "Patient",
                "Status"
        };

        Object[][] data =
                new Object[doctor.getAppointments().size()][4];

        for (int i = 0;
             i < doctor.getAppointments().size();
             i++) {

            Appointment appointment =
                    doctor.getAppointments().get(i);

            data[i][0] = appointment.getDate();
            data[i][1] = appointment.getTime();
            data[i][2] =
                    appointment.getPatient().getName();
            data[i][3] =
                    appointment.getApStatus();
        }

        appointmentsTable.setModel(
                new javax.swing.table.DefaultTableModel(
                        data,
                        titles
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {
                        return false;
                    }
                }
        );
    }
}