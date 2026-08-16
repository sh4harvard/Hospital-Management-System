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

    private final HospitalPanel hospitalPanel;

    private final WardPanel wardPanel;
    private final WardDetailPanel wardDetailPanel;
    private final WardEditPanel wardEditPanel;


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


        wardPanel = new WardPanel(hospital, this);
        wardDetailPanel = new WardDetailPanel(hospital, this);
        wardEditPanel = new WardEditPanel(hospital, this);
        add(wardPanel, "WARDS");
        add(wardDetailPanel, "WARD_DETAIL");
        add(wardEditPanel, "WARD_EDIT");

        hospitalPanel = new HospitalPanel(hospital, this);
        add(hospitalPanel, "HOSPITAL");

        appointmentPanel = new HAppointmentsPanel(hospital, this);
        add(appointmentPanel, "APPOINTMENTS");
        appointmentAddPanel = new AppointmentAddPanel(hospital, this);
        add(appointmentAddPanel, "APPOINTMENT_ADD");

        showPanel("HOSPITAL");

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
        doctorAddPanel.refreshWards();
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

        System.out.println("SHOW APPOINTMENTS");

        appointmentPanel.refreshTable();

        System.out.println("REFRESH DONE");

        showPanel("APPOINTMENTS");

        System.out.println("SHOW PANEL DONE");

        System.out.println(
                "Visible card: " + appointmentPanel.isVisible()
        );
    }

    public void showAppointmentAdd() {
        appointmentAddPanel.refreshFields();
        showPanel("APPOINTMENT_ADD");
    }

    public void showWards() {
        wardPanel.refreshTable();
        showPanel("WARDS");
    }

    public void showWardDetail(Ward ward) {
        wardDetailPanel.setWard(ward);
        showPanel("WARD_DETAIL");
    }

    public void showWardEdit(Ward ward) {
        wardEditPanel.setWard(ward);
        showPanel("WARD_EDIT");
    }

    public void showHospital() {
        hospitalPanel.refresh();
        showPanel("HOSPITAL");
    }

}

