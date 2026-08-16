package Hospital.GUI;

import Hospital.Core.HospitalSystem;

import javax.swing.*;
import java.awt.*;

public class HospitalPanel extends JPanel{

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;
    private final HospitalBillPanel billingPanel;
    private final WardPanel wardPanel;

    private JLabel patientCount;
    private JLabel doctorCount;
    private JLabel wardCount;


    public HospitalPanel(HospitalSystem hospital, ContentPanel contentPanel){
        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(0, 20));
        setBackground(AppColors.BACKGROUND);

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(AppColors.BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 0, 25));
        JLabel title = new JLabel("Hospital Overview");
        UIStyle.styleTitle(title);

        headerPanel.add(title, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);


        JPanel mainBody = new JPanel(new BorderLayout(0, 20));

        mainBody.setBackground(AppColors.BACKGROUND);

        mainBody.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));

        //

        JPanel hospitalOverviewPanel = new JPanel(new GridLayout(1, 3, 15, 0));

        hospitalOverviewPanel.setBackground(AppColors.BACKGROUND);


        // Patient card
        JPanel patientStat = new JPanel(new GridLayout(2, 1));

        patientStat.setBackground(AppColors.WHITE);
        patientStat.setBorder(UIStyle.createPanelBorder());

        JLabel patientTitle = new JLabel("PATIENTS", JLabel.CENTER);

        patientTitle.setFont(AppFonts.NORMAL);
        patientTitle.setForeground(AppColors.SECONDARY_TEXT);

        patientCount = new JLabel(String.valueOf(hospital.getPatients().size()), JLabel.CENTER);

        patientCount.setFont(AppFonts.LARGE_NUMBER);
        patientCount.setForeground(AppColors.DARK_BLUE);

        patientStat.add(patientTitle);
        patientStat.add(patientCount);

        hospitalOverviewPanel.add(patientStat);


        // Doctor card
        JPanel doctorStat = new JPanel(new GridLayout(2, 1));

        doctorStat.setBackground(AppColors.WHITE);
        doctorStat.setBorder(UIStyle.createPanelBorder());

        JLabel doctorTitle = new JLabel("DOCTORS", JLabel.CENTER);

        doctorTitle.setFont(AppFonts.NORMAL);
        doctorTitle.setForeground(AppColors.SECONDARY_TEXT);

        doctorCount = new JLabel(String.valueOf(hospital.getDoctors().size()), JLabel.CENTER);

        doctorCount.setFont(AppFonts.LARGE_NUMBER);
        doctorCount.setForeground(AppColors.DARK_BLUE);

        doctorStat.add(doctorTitle);
        doctorStat.add(doctorCount);

        hospitalOverviewPanel.add(doctorStat);


        // Ward card
        JPanel wardStat = new JPanel(new GridLayout(2, 1));

        wardStat.setBackground(AppColors.WHITE);
        wardStat.setBorder(UIStyle.createPanelBorder());

        JLabel wardTitle = new JLabel("WARDS", JLabel.CENTER);

        wardTitle.setFont(AppFonts.NORMAL);
        wardTitle.setForeground(AppColors.SECONDARY_TEXT);

        wardCount = new JLabel(String.valueOf(hospital.getWards().size()), JLabel.CENTER);

        wardCount.setFont(AppFonts.LARGE_NUMBER);
        wardCount.setForeground(AppColors.DARK_BLUE);

        wardStat.add(wardTitle);
        wardStat.add(wardCount);

        hospitalOverviewPanel.add(wardStat);

        mainBody.add(hospitalOverviewPanel, BorderLayout.NORTH);
        //


        JTabbedPane hinfoTabs = new JTabbedPane();

        wardPanel = new WardPanel(hospital, contentPanel);
        hinfoTabs.add("Wards", wardPanel);
        hinfoTabs.add("Medical Services", new MedicalServicePanel(hospital));
        billingPanel = new HospitalBillPanel(hospital);
        hinfoTabs.add("Billing", billingPanel);



        hinfoTabs.setBorder(BorderFactory.createLineBorder(AppColors.BORDER));

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

        wardPanel.refreshTable();

        billingPanel.refreshTable();
    }
}
