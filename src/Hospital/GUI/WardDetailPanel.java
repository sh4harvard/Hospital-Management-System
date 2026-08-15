package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;
import Hospital.Core.Ward;

import javax.swing.*;
import java.awt.*;

public class WardDetailPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private Ward ward;

    private JLabel wardID;
    private JLabel nameLabel;
    private JLabel capacityLabel;
    private JLabel patientsLabel;
    private JLabel doctorsLabel;

    public WardDetailPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel =
                new JPanel(new BorderLayout());

        JButton backBtn =
                new JButton("<- Wards");

        wardID = new JLabel();

        wardID.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        headerPanel.add(
                backBtn,
                BorderLayout.WEST
        );

        headerPanel.add(
                wardID,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // Main
        JPanel mainPanel =
                new JPanel(new BorderLayout());

        JPanel infoPanel =
                new JPanel(
                        new GridLayout(4, 2, 10, 10)
                );

        infoPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        infoPanel.add(new JLabel("Name:"));

        nameLabel = new JLabel();
        infoPanel.add(nameLabel);


        infoPanel.add(new JLabel("Capacity:"));

        capacityLabel = new JLabel();
        infoPanel.add(capacityLabel);


        infoPanel.add(new JLabel("Patients:"));

        patientsLabel = new JLabel();
        infoPanel.add(patientsLabel);


        infoPanel.add(new JLabel("Doctors:"));

        doctorsLabel = new JLabel();
        infoPanel.add(doctorsLabel);


        mainPanel.add(
                infoPanel,
                BorderLayout.NORTH
        );


        // Patient and Doctor lists
        JPanel listsPanel =
                new JPanel(
                        new GridLayout(1, 2, 20, 0)
                );


        // Patients
        JPanel patientPanel =
                new JPanel(new BorderLayout());

        patientPanel.add(
                new JLabel("Patients"),
                BorderLayout.NORTH
        );

        JList<String> patientList =
                new JList<>();

        patientPanel.add(
                new JScrollPane(patientList),
                BorderLayout.CENTER
        );


        // Doctors
        JPanel doctorPanel =
                new JPanel(new BorderLayout());

        doctorPanel.add(
                new JLabel("Doctors"),
                BorderLayout.NORTH
        );

        JList<String> doctorList =
                new JList<>();

        doctorPanel.add(
                new JScrollPane(doctorList),
                BorderLayout.CENTER
        );


        listsPanel.add(patientPanel);
        listsPanel.add(doctorPanel);

        mainPanel.add(
                listsPanel,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // Back button
        backBtn.addActionListener(e ->
                contentPanel.showWards()
        );
    }


    public void setWard(Ward ward) {

        this.ward = ward;

        wardID.setText(
                "Ward #" + ward.getId()
        );

        nameLabel.setText(
                ward.getName()
        );

        capacityLabel.setText(
                String.valueOf(
                        ward.getCapacity()
                )
        );

        patientsLabel.setText(
                ward.getPatients().size()
                        + " / "
                        + ward.getCapacity()
        );

        doctorsLabel.setText(
                String.valueOf(
                        ward.getDoctors().size()
                )
        );
    }
}