package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel(ContentPanel contentPanel){

        setLayout(new BorderLayout());

        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new GridLayout(6,1));

        JButton hospitalBtn = new JButton("Hospital");
        JButton patientBtn = new JButton("Patients");
        JButton doctorBtn = new JButton("Doctors");
        JButton appointmentBtn = new JButton("Appointments");

        sectionPanel.add(hospitalBtn);
        sectionPanel.add(patientBtn);
        sectionPanel.add(doctorBtn);
        sectionPanel.add(appointmentBtn);

        // Action
        hospitalBtn.addActionListener(e -> {
            contentPanel.showPanel("HOSPITAL");
        });
        patientBtn.addActionListener(e -> {
            contentPanel.showPanel("PATIENTS");
        });
        doctorBtn.addActionListener(e -> {
            contentPanel.showPanel("DOCTORS");
        });
        appointmentBtn.addActionListener(e -> {
            contentPanel.showPanel("APPOINTMENTS");
        });

        JPanel exitPanel = new JPanel();

        JButton exitBtn = new JButton("EXIT");
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
        exitPanel.add(exitBtn);

        add(sectionPanel, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);
    }
}
