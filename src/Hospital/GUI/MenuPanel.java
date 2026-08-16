package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private JButton selectedButton;

    public MenuPanel(ContentPanel contentPanel) {

        setLayout(new BorderLayout());

        setBackground(AppColors.DARK_BLUE);

        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));


        JPanel sectionPanel = new JPanel();

        sectionPanel.setLayout(
                new GridLayout(6, 1, 0, 10)
        );

        sectionPanel.setBackground(
                AppColors.DARK_BLUE
        );


        JButton hospitalBtn =
                new JButton("Hospital");

        JButton patientBtn =
                new JButton("Patients");

        JButton doctorBtn =
                new JButton("Doctors");

        JButton appointmentBtn =
                new JButton("Appointments");



        UIStyle.styleMenuButton(hospitalBtn);
        UIStyle.styleMenuButton(patientBtn);
        UIStyle.styleMenuButton(doctorBtn);
        UIStyle.styleMenuButton(appointmentBtn);


        sectionPanel.add(hospitalBtn);
        sectionPanel.add(patientBtn);
        sectionPanel.add(doctorBtn);
        sectionPanel.add(appointmentBtn);


        sectionPanel.add(new JLabel());
        sectionPanel.add(new JLabel());



        hospitalBtn.addActionListener(e -> {
            selectButton(hospitalBtn);
            contentPanel.showHospital();
        });

        patientBtn.addActionListener(e -> {
            selectButton(patientBtn);
            contentPanel.showPatients();
        });

        doctorBtn.addActionListener(e -> {
            selectButton(doctorBtn);
            contentPanel.showDoctors();
        });

        appointmentBtn.addActionListener(e -> {
            selectButton(appointmentBtn);
            contentPanel.showAppointments();
        });



        JPanel exitPanel = new JPanel();

        exitPanel.setBackground(
                AppColors.DARK_BLUE
        );


        JButton exitBtn =
                new JButton("EXIT");

        UIStyle.styleMenuButton(exitBtn);

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        exitPanel.add(exitBtn);


        add(
                sectionPanel,
                BorderLayout.CENTER
        );

        add(
                exitPanel,
                BorderLayout.SOUTH
        );
    }

    private void selectButton(JButton button) {

        if (selectedButton != null) {
            selectedButton.setBackground(AppColors.WHITE);
            selectedButton.setForeground(AppColors.DARK_BLUE);
        }

        selectedButton = button;

        selectedButton.setBackground(AppColors.PRIMARY_BLUE);
        selectedButton.setForeground(AppColors.WHITE);
    }
}