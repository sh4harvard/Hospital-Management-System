package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientDetailPanel extends JPanel {

    private final HospitalSystem hospital;
    private final ContentPanel contentPanel;

    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel phoneLabel;
    private JLabel wardLabel;

    private final MedicalRecordPanel medicalRecordPanel;
    private final BillPanel billPanel;
    private final AppointmentsPanel appointmentsPanel;

    private Patient patient;

    public PatientDetailPanel(HospitalSystem hospital, ContentPanel contentPanel) {
        this.hospital = hospital;
        this.contentPanel = contentPanel;

        medicalRecordPanel = new MedicalRecordPanel();
        billPanel = new BillPanel();
        appointmentsPanel = new AppointmentsPanel();


        setLayout(new BorderLayout());


        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton backtoBtn = new JButton("<- Patients");
        backtoBtn.addActionListener(e ->
            contentPanel.showPatients()
        );

        JLabel patientID = new JLabel("Patient #10");

        headerPanel.add(backtoBtn, BorderLayout.WEST);
        headerPanel.add(patientID, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);


        // Main Body
        JPanel patientDisplayINFO = new JPanel(new BorderLayout());

        //
        JPanel basicINFO = new JPanel(new BorderLayout());

        JPanel patientIdentity = new JPanel(new GridLayout(0, 1));

        patientIdentity.add(namePatient);
        patientIdentity.add(idPatient);

        basicINFO.add(patientIdentity, BorderLayout.NORTH);

        //

        JPanel patientINFO = new JPanel(new BorderLayout());

        JLabel personalInfo = new JLabel("Personal Informetion");
        patientINFO.add(personalInfo, BorderLayout.NORTH);

        JPanel infoTable = new JPanel(new GridLayout(2, 4));

        patientINFO.add(infoTable, BorderLayout.CENTER);

        basicINFO.add(patientINFO, BorderLayout.CENTER);

        patientDisplayINFO.add(basicINFO, BorderLayout.NORTH);

        //

        JPanel detailINFO = new JPanel();

        JTabbedPane infotabs = new JTabbedPane();


        infotabs.add("Medical Record", new MedicalRecordPanel());
        infotabs.add("Bill", new BillPanel());
        infotabs.add("Appointments", new AppointmentsPanel());


        // Footer

    }

    public void setPatient(Patient patient) {

        this.patient = patient;

        updatePatientInformation();

        medicalRecordPanel.setPatient(patient);
        billPanel.setPatient(patient);
        appointmentsPanel.setPatient(patient);
    }

    private void updatePatientInformation() {

        if (patient == null) {
            return;
        }

        nameLabel.setText(patient.getName());
        ageLabel.setText(String.valueOf(patient.getAge()));
        genderLabel.setText(patient.getGender().toString());
        phoneLabel.setText(patient.getPhoneNumber());

        if (patient.getWard() != null) {
            wardLabel.setText(patient.getWard().getName());
        } else {
            wardLabel.setText("None");
        }
    }
}
