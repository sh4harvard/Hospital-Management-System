package Hospital;

import Hospital.enums.Gender;

import javax.swing.*;
import java.util.ArrayList;

public class Patient extends Person implements DisplayInformation{

    private MedicalRecord medicalRecord;
    private Bill bill;
    private Ward ward;
    private ArrayList<Appointment> appointments;

    public Patient(String id, String name, int age, Gender gender, String phoneNumber){
        super(id, name, age, gender, phoneNumber);

        medicalRecord = new MedicalRecord();
        bill = new Bill();
        appointments = new ArrayList<>();
    }

    @Override
    public String showInfo() {
        String app = "";
        for (Appointment ap : appointments) {
            app += ap.showInfo();
        }

        return "===== Patient's Information =====" +
            "\nID: " + getId() +
            "\nAge: " + getAge() +
            "\nName: " + getName() +
            "\nGender: " + getGender() +
            "\nPhone: " + getPhoneNumber() +
            "\nMedical Information: " + medicalRecord.showInfo() +
            "\nBill: " + bill.showInfo() +
            "\nWard: " + ward.showInfo() +
            "\nAppointments: " + app;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public Bill getBill() {
        return bill;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
}
