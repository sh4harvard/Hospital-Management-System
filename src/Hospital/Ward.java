package Hospital;

import java.util.ArrayList;

public class Ward implements DisplayInformation{

    private String name;
    private int capacity;

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;

    public Ward(String name, int capacity){
        this.name = name;
        this.capacity = capacity;

        patients = new ArrayList<>();
        doctors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public boolean addPatient(Patient patient) {
        if (patients.size() >= capacity) {
            return false;
        }

        patients.add(patient);
        patient.setWard(this);
        return true;
    }

    public void removePatient(Patient patient){
        patients.remove(patient);
        patient.setWard(null);
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.setWard(this);
    }

    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
        doctor.setWard(null);
    }


    public String showInfo(){
        System.out.println(
            "===== Ward (" + name + ") Information =====" +
            "\nCapacity: " + capacity +
            "\nCurrent Patients: " + patients.size() +
            "\nDoctors: " + doctors.size());
    }

}
