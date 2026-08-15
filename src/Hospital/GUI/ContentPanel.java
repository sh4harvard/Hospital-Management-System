package Hospital.GUI;

import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;

    private final HospitalSystem hospital;

    private final PatientPanel patientPanel;
    private final PatientAddPanel patientAddPanel;
    private final PatientDetailPanel patientDetailPanel;
    private final PatientEditPanel patientEditPanel;

    public ContentPanel(HospitalSystem hospital){

        this.hospital = hospital;

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        patientPanel = new PatientPanel(hospital, this);
        patientAddPanel = new PatientAddPanel(hospital, this);
        patientDetailPanel = new PatientDetailPanel(hospital, this);
        patientEditPanel = new PatientEditPanel(hospital, this);

        add(patientPanel, "PATIENTS");
        add(patientAddPanel, "PATIENT_ADD");
        add(patientDetailPanel, "PATIENT_DETAIL");
        add(patientEditPanel, "PATIENT_EDIT");

        add(new HospitalPanel(), "HOSPITAL");
        add(new PatientPanel(hospital), "PATIENTS");
        add(new DoctorPanel(), "DOCTORS");
        add(new HAppointmentsPanel(), "APPOINTMENTS");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }


    public void showPatients() {
        patientPanel.refreshTable();
        showPanel("PATIENTS");
    }

    public void showPatientAdd() {
        patientAddPanel.clearFields();
        showPanel("PATIENT_ADD");
    }

    public void showPatientDetail(Patient patient) {
        patientDetailPanel.setPatient(patient);
        showPanel("PATIENT_DETAIL");
    }

    public void showPatientEdit(Patient patient) {
        patientEditPanel.setPatient(patient);
        showPanel("PATIENT_EDIT");
    }
}
