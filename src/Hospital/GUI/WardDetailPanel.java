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

    private JList<Patient> patientList;
    private DefaultListModel<Patient> patientListModel;

    private JList<Doctor> doctorList;
    private DefaultListModel<Doctor> doctorListModel;

    public WardDetailPanel(
            HospitalSystem hospital,
            ContentPanel contentPanel) {

        this.hospital = hospital;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout());

        //header

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


        // Mainbody

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




        JPanel listsPanel =
                new JPanel(
                        new GridLayout(1, 2, 20, 0)
                );


        // patients

        JPanel patientPanel =
                new JPanel(new BorderLayout());

        patientPanel.add(
                new JLabel("Patients"),
                BorderLayout.NORTH
        );


        patientListModel =
                new DefaultListModel<>();

        patientList =
                new JList<>(patientListModel);

        patientList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        patientPanel.add(
                new JScrollPane(patientList),
                BorderLayout.CENTER
        );


        // Discharge button

        JButton dischargeBtn =
                new JButton("Discharge Patient");

        dischargeBtn.addActionListener(e ->
                dischargeSelectedPatient()
        );

        JPanel patientButtonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        patientButtonPanel.add(dischargeBtn);

        patientPanel.add(
                patientButtonPanel,
                BorderLayout.SOUTH
        );


        // doctors

        JPanel doctorPanel =
                new JPanel(new BorderLayout());

        doctorPanel.add(
                new JLabel("Doctors"),
                BorderLayout.NORTH
        );


        doctorListModel =
                new DefaultListModel<>();

        doctorList =
                new JList<>(doctorListModel);

        doctorList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        doctorPanel.add(
                new JScrollPane(doctorList),
                BorderLayout.CENTER
        );

        JButton dischargeDoctorBtn =
                new JButton("Discharge Doctor");

        dischargeDoctorBtn.addActionListener(e ->
                dischargeSelectedDoctor()
        );

        JPanel doctorButtonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        doctorButtonPanel.add(dischargeDoctorBtn);

        doctorPanel.add(
                doctorButtonPanel,
                BorderLayout.SOUTH
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




        backBtn.addActionListener(e ->
                contentPanel.showHospital()
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



        patientListModel.clear();

        for (Patient patient :
                ward.getPatients()) {

            patientListModel.addElement(patient);
        }



        doctorListModel.clear();

        for (Doctor doctor :
                ward.getDoctors()) {

            doctorListModel.addElement(doctor);
        }
    }



    private void dischargeSelectedPatient() {

        if (ward == null) {
            return;
        }


        Patient patient =
                patientList.getSelectedValue();


        if (patient == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient first."
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Discharge "
                                + patient.getName()
                                + " from "
                                + ward.getName()
                                + "?",
                        "Discharge Patient",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (result != JOptionPane.YES_OPTION) {
            return;
        }


        String patientName =
                patient.getName();

        String wardName =
                ward.getName();



        hospital.dischargePatient(patient);

        System.out.println(
                "AFTER DISCHARGE - Patient ward: "
                        + patient.getWard()
        );

        System.out.println(
                "AFTER DISCHARGE - Ward patients: "
                        + ward.getPatients().size()
        );

        System.out.println(
                "AFTER DISCHARGE - Hospital incomes: "
                        + hospital.getHospitalIncomes().size()
        );

        System.out.println(
                "AFTER DISCHARGE - Ward bonus: "
                        + hospital.findWardBonusByWardId(
                        ward.getId()
                )
        );



        setWard(ward);



        JOptionPane.showMessageDialog(
                this,
                "Patient discharged successfully.\n\n"
                        + "Patient: "
                        + patientName
                        + "\nWard: "
                        + wardName,
                "Discharge Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void dischargeSelectedDoctor() {

        if (ward == null) {
            return;
        }

        Doctor doctor =
                doctorList.getSelectedValue();

        if (doctor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first."
            );

            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Discharge "
                                + doctor.getName()
                                + " from "
                                + ward.getName()
                                + "?",
                        "Discharge Doctor",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        String doctorName = doctor.getName();
        String wardName = ward.getName();

        hospital.dischargeDoctor(doctor);

        setWard(ward);

        JOptionPane.showMessageDialog(
                this,
                "Doctor discharged successfully.\n\n"
                        + "Doctor: "
                        + doctorName
                        + "\nWard: "
                        + wardName,
                "Discharge Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}