package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class DoctorDetailPanel extends JPanel {
    public DoctorDetailPanel(){

        setLayout(new BorderLayout());


        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton backtoBtn = new JButton("<- Doctors");
        JLabel doctorID = new JLabel("Doctor #101");

        doctorID.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        headerPanel.add(backtoBtn, BorderLayout.WEST);
        headerPanel.add(doctorID, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);


        // Main Body
        JPanel mainBodyPanel = new JPanel(new BorderLayout());


        JPanel infoPanel = new JPanel(new BorderLayout());

        //
        JPanel identityPanel = new JPanel(new GridLayout(0, 1));
        JLabel nameDoctor = new JLabel(" 'Doctor Icon' Dr. Ali Ahmadi");
        nameDoctor.setFont(new Font("Arial", Font.BOLD, 25));
        JLabel idDoctor = new JLabel("Doctor ID: 101");
        identityPanel.add(nameDoctor);
        identityPanel.add(idDoctor);
        infoPanel.add(idDoctor, BorderLayout.NORTH);

        //
        JPanel infoProfessPersonalPanel = new JPanel(new GridLayout(1, 2));

        JPanel personalPanel = new JPanel(new BorderLayout());
        personalPanel.add(new JLabel("Personal"), BorderLayout.NORTH);
        JPanel personalINFOPanel = new JPanel(new GridLayout(3, 2));

        personalINFOPanel.add(new JLabel("Age:"));
        personalINFOPanel.add(new JLabel("40"));
        personalINFOPanel.add(new JLabel("Gender:"));
        personalINFOPanel.add(new JLabel("Male"));
        personalINFOPanel.add(new JLabel("Phone:"));
        personalINFOPanel.add(new JLabel("09128394039"));
        personalPanel.add(personalINFOPanel, BorderLayout.CENTER);

        infoProfessPersonalPanel.add(personalPanel);


        JPanel professionalPanel = new JPanel(new BorderLayout());
        professionalPanel.add(new JLabel("Professional"), BorderLayout.NORTH);
        JPanel professionalINFOPanel = new JPanel(new GridLayout(4, 2));

        professionalINFOPanel.add(new JLabel("Specialty:"));
        professionalINFOPanel.add(new JLabel("Cardiology"));
        professionalINFOPanel.add(new JLabel("Ward:"));
        professionalINFOPanel.add(new JLabel("Cardiology"));
        professionalINFOPanel.add(new JLabel("Shift:"));
        professionalINFOPanel.add(new JLabel("08:00 - 16:00"));
        professionalINFOPanel.add(new JLabel("Capacity:"));
        professionalINFOPanel.add(new JLabel("20/day"));
        professionalPanel.add(professionalINFOPanel, BorderLayout.CENTER);

        infoProfessPersonalPanel.add(professionalPanel);

        infoPanel.add(infoProfessPersonalPanel, BorderLayout.CENTER);

        mainBodyPanel.add(infoPanel, BorderLayout.NORTH);

        //

        JPanel appointmentPanel = new JPanel(new BorderLayout());

        appointmentPanel.add(new JLabel("Appointments"), BorderLayout.NORTH);


        String[] titles = {
            "Date",
            "Time",
            "Patient",
            "Status"
        };
        Object[][] data = {
            {"20 Mar 2026", "10:00", "Ali Ahmadi", "Scheduled"},
            {"10 Nov 2026", "11:00", "Sara Akbary", "Completed"}
        };
        JTable appointmentsTable = new JTable(data, titles);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        appointmentPanel.add(scrollPane, BorderLayout.CENTER);

        mainBodyPanel.add(appointmentPanel, BorderLayout.CENTER);

        //

        add(mainBodyPanel, BorderLayout.CENTER);
    }
}
