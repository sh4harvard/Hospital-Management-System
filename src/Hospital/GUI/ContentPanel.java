package Hospital.GUI;

import Hospital.Core.Doctor;
import Hospital.Core.HospitalSystem;
import Hospital.Core.Patient;
import Hospital.Core.Ward;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;

    private final HospitalSystem hospital;

    private final PatientPanel patientPanel;
    private final PatientAddPanel patientAddPanel;
    private final PatientDetailPanel patientDetailPanel;
    private final PatientEditPanel patientEditPanel;

    private final DoctorPanel doctorPanel;
    private final DoctorAddPanel doctorAddPanel;
    private final DoctorDetailPanel doctorDetailPanel;
    private final DoctorEditPanel doctorEditPanel;

    private final HAppointmentsPanel appointmentPanel;
    private final AppointmentAddPanel appointmentAddPanel;


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


        doctorPanel = new DoctorPanel(hospital, this);
        doctorAddPanel = new DoctorAddPanel(hospital, this);
        doctorDetailPanel = new DoctorDetailPanel(hospital, this);
        doctorEditPanel = new DoctorEditPanel(hospital, this);

        add(doctorPanel, "DOCTORS");
        add(doctorAddPanel, "DOCTOR_ADD");
        add(doctorDetailPanel, "DOCTOR_DETAIL");
        add(doctorEditPanel, "DOCTOR_EDIT");


        add(new HospitalPanel(hospital), "HOSPITAL");

        appointmentPanel = new HAppointmentsPanel(hospital, this);
        add(appointmentPanel, "APPOINTMENTS");
        appointmentAddPanel = new AppointmentAddPanel(hospital, this);
        add(appointmentAddPanel, "APPOINTMENT_ADD");

    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }


    public void showPatients() {

        System.out.println("ContentPanel.showPatients()");
        System.out.println("Patients in hospital: " +
                hospital.getPatients().size());
        System.out.println("PatientPanel: " +
                patientPanel);

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

    public void showDoctors() {
        doctorPanel.refreshTable();
        showPanel("DOCTORS");
    }

    public void showDoctorAdd() {
        showPanel("DOCTOR_ADD");
    }

    public void showDoctorDetail(Doctor doctor) {
        doctorDetailPanel.setDoctor(doctor);
        showPanel("DOCTOR_DETAIL");
    }

    public void showDoctorEdit(Doctor doctor) {
        doctorEditPanel.setDoctor(doctor);
        showPanel("DOCTOR_EDIT");
    }

    public void showAppointments() {
        appointmentPanel.refreshTable();
        showPanel("APPOINTMENTS");
    }

    public void showAppointmentAdd() {
        appointmentAddPanel.refreshFields();
        showPanel("APPOINTMENT_ADD");
    }




}
