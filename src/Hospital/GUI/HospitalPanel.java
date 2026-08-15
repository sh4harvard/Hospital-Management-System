package Hospital.GUI;

import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;

public class HospitalPanel extends JPanel{

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;
    private final HospitalBillPanel billingPanel;

    private JLabel patientCount;
    private JLabel doctorCount;
    private JLabel wardCount;


    public HospitalPanel(HospitalSystem hospital, ContentPanel contentPanel){
        this.hospital = hospital;
        this.contentPanel = contentPanel;

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
        patientCount = new JLabel(String.valueOf(hospital.getPatients().size()), JLabel.CENTER);        patientCount.setFont(new Font("Arial", Font.BOLD, 30));
        patientStat.add(patientTitle);
        patientStat.add(patientCount);

        hospitalOverviewPanel.add(patientStat);

        JPanel doctorStat = new JPanel(new GridLayout(2, 1));
        JLabel doctorTitle = new JLabel("DOCTORS", JLabel.CENTER);
        doctorTitle.setFont(new Font("Arial", Font.BOLD, 15));
        doctorCount = new JLabel(String.valueOf(hospital.getDoctors().size()), JLabel.CENTER);        doctorCount.setFont(new Font("Arial", Font.BOLD, 30));
        doctorStat.add(doctorTitle);
        doctorStat.add(doctorCount);

        hospitalOverviewPanel.add(doctorStat);

        JPanel wardStat = new JPanel(new GridLayout(2, 1));
        JLabel wardTitle = new JLabel("Ward", JLabel.CENTER);
        wardTitle.setFont(new Font("Arial", Font.BOLD, 15));
        wardCount = new JLabel(String.valueOf(hospital.getWards().size()), JLabel.CENTER);        wardCount.setFont(new Font("Arial", Font.BOLD, 30));
        wardStat.add(wardTitle);
        wardStat.add(wardCount);

        hospitalOverviewPanel.add(wardStat);


        mainBody.add(hospitalOverviewPanel, BorderLayout.NORTH);
        //


        JTabbedPane hinfoTabs = new JTabbedPane();

        hinfoTabs.add("Wards", new WardPanel(hospital, contentPanel));
        hinfoTabs.add("Medical Services", new MedicalServicePanel(hospital));
        billingPanel = new HospitalBillPanel(hospital);
        hinfoTabs.add("Billing", billingPanel);



        mainBody.add(hinfoTabs, BorderLayout.CENTER);


        add(mainBody, BorderLayout.CENTER);
    }

    public void refreshBilling() {
        billingPanel.refreshTable();
    }

    public void refresh() {

        patientCount.setText(
                String.valueOf(hospital.getPatients().size())
        );

        doctorCount.setText(
                String.valueOf(hospital.getDoctors().size())
        );

        wardCount.setText(
                String.valueOf(hospital.getWards().size())
        );

        billingPanel.refreshTable();
    }
}
