package Hospital.GUI;

import javax.swing.*;
import java.awt.*;

public class HospitalPanel extends JPanel{
    public HospitalPanel(){

        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel();
        JLabel title = new JLabel("Hospital Overview");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);


        JPanel mainBody = new JPanel(new BorderLayout());

        //

        JPanel hospitalOverviewPanel = new JPanel(new GridLayout(1, 3, 15, 0));

        JPanel patientStat = new JPanel(new GridLayout(2, 1));
        JLabel patientTitle = new JLabel("PATIENTS", JLabel.CENTER);
        patientTitle.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel patientCount = new JLabel("120", JLabel.CENTER);
        patientCount.setFont(new Font("Arial", Font.BOLD, 30));
        patientStat.add(patientTitle);
        patientStat.add(patientCount);

        hospitalOverviewPanel.add(patientStat);

        JPanel doctorStat = new JPanel(new GridLayout(2, 1));
        JLabel doctorTitle = new JLabel("DOCTORS", JLabel.CENTER);
        doctorTitle.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel doctorCount = new JLabel("50", JLabel.CENTER);
        doctorCount.setFont(new Font("Arial", Font.BOLD, 30));
        doctorStat.add(doctorTitle);
        doctorStat.add(doctorCount);

        hospitalOverviewPanel.add(doctorStat);

        JPanel wardStat = new JPanel(new GridLayout(2, 1));
        JLabel wardTitle = new JLabel("Ward", JLabel.CENTER);
        wardTitle.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel wardCount = new JLabel("20", JLabel.CENTER);
        wardCount.setFont(new Font("Arial", Font.BOLD, 30));
        wardStat.add(wardTitle);
        wardStat.add(wardCount);

        hospitalOverviewPanel.add(wardStat);


        mainBody.add(hospitalOverviewPanel, BorderLayout.NORTH);
        //


        JTabbedPane hinfoTabs = new JTabbedPane();

        hinfoTabs.add("Wards", new WardPanel());
        hinfoTabs.add("Medical Services", new MedicalServicePanel());
        hinfoTabs.add("Billing", new HospitalBillPanel());

        mainBody.add(hinfoTabs, BorderLayout.CENTER);


        add(mainBody, BorderLayout.CENTER);
    }
}
