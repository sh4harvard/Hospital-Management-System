package Hospital.Core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import Hospital.Core.enums.AppointmentStatus;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ArrayList<Ward> wards;
    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalService> medicalServices;
    private ArrayList<HospitalIncome> hospitalIncomes;

    // IDs
    private int nextPatientId = 1;
    private int nextDoctorId = 1;
    private int nextWardId = 1;
    private int nextMedicalServiceId = 1;
    private int nextChargeId = 1;
    private int nextAppointmentId = 1;


    public HospitalSystem(){
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        wards = new ArrayList<>();
        appointments = new ArrayList<>();
        medicalServices = new ArrayList<>();
        hospitalIncomes = new ArrayList<>();
    }

    // ID

    public void setNextPatientId(int id) {
        this.nextPatientId = id;
    }
    public void setNextDoctorId(int id) {
        this.nextDoctorId = id;
    }
    public void setNextWardId(int id) {
        this.nextWardId = id;
    }
    public void setNextMedicalServiceId(int id) {
        this.nextMedicalServiceId = id;
    }
    public void setNextChargeId(int id) {
        this.nextChargeId = id;
    }
    public void setNextAppointmentId(int id) {
        this.nextAppointmentId = id;
    }


    public void updateNextIds() {

        int maxPatientId = 0;
        for (Patient patient : patients) {
            if (patient.getId() > maxPatientId) {
                maxPatientId = patient.getId();
            }
        }
        nextPatientId = maxPatientId + 1;


        int maxDoctorId = 0;
        for (Doctor doctor : doctors) {
            if (doctor.getId() > maxDoctorId) {
                maxDoctorId = doctor.getId();
            }
        }
        nextDoctorId = maxDoctorId + 1;


        int maxWardId = 0;
        for (Ward ward : wards) {
            if (ward.getId() > maxWardId) {
                maxWardId = ward.getId();
            }
        }
        nextWardId = maxWardId + 1;


        int maxServiceId = 0;
        for (MedicalService service : medicalServices) {
            if (service.getId() > maxServiceId) {
                maxServiceId = service.getId();
            }
        }
        nextMedicalServiceId = maxServiceId + 1;


        int maxChargeId = 0;

        for (Patient patient : patients) {
            for (Charge charge : patient.getBill().getCharges()) {
                if (charge.getId() > maxChargeId) {
                    maxChargeId = charge.getId();
                }
            }
        }

        nextChargeId = maxChargeId + 1;


        int maxAppointmentId = 0;

        for (Appointment appointment : appointments) {
            if (appointment.getId() > maxAppointmentId) {
                maxAppointmentId = appointment.getId();
            }
        }

        nextAppointmentId = maxAppointmentId + 1;
    }


    public int generatePatientId() {
        int id = nextPatientId++;
        return id;
    }
    public int generateDoctorId() {
        int id = nextDoctorId++;
        return id;
    }
    public int generateWardId() {
        int id = nextWardId++;
        return id;
    }
    public int generateMedicalServiceId() {
        int id = nextMedicalServiceId++;
        return id;
    }
    public int generateChargeId() {
        int id = nextChargeId++;
        return id;
    }
    public int generateAppointmentId() {
        int id = nextAppointmentId++;
        return id;
    }


    // Patient Section

    public ArrayList<Patient> getPatients() {return patients;}

    public void addPatient(Patient patient){
        patients.add(patient);
    }

    public void removePatient(Patient patient){
        patients.remove(patient);
    }

    public Patient findPatientById(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void admitPatient(Patient patient, Ward ward){
        if (ward.getCapacity() > ward.getPatients().size()){
            patient.setWard(ward);
            System.out.println("success");
            return;
        }
        System.out.println("Wars full");
    }

    public void dischargePatient(Patient patient){
        Ward pw = patient.getWard();
        if (pw != null) {
            if (pw.getPatients().size() == 1) {
                System.out.println("Successfully Ward empty");
                hospitalIncomes.add(new WardBonus(pw));
            }
            pw.removePatient(patient);
        }
    }


    // Doctor Section

    public Doctor findDoctorbyId(int id) {
        for (Doctor d : doctors) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public ArrayList<Doctor> getDoctors() {return doctors;}

    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }

    //findDoctorById(String id)


    // Ward Section

    public ArrayList<Ward> getWards() {return wards;}

    public void addWard(Ward ward){
        wards.add(ward);
    }

    public Ward findWardbyName(String name){
        for (Ward w: wards){
            if (w.getName().equals(name)){
                return w;
            }
        }
        return null;
    }

    public Ward findWardById(int id) {
        for (Ward w : wards) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }

    public void transferWardPatient(Patient patient, Ward newWard){
        dischargePatient(patient);
        patient.setWard(newWard);
    }

    public void transferWardDoctor(Doctor doctor, Ward newWard){
        if (doctor.getWard() != null){
            doctor.getWard().removeDoctor(doctor);
        }
        doctor.setWard(newWard);
    }

    // Appointment Section

    public ArrayList<Appointment> getAppointments() {return appointments;}

    public Appointment createAppointment(int id, Patient patient, Doctor doctor, LocalDate date, LocalTime time){
        if (patient.getWard() == null || patient.getWard() == doctor.getWard()){
            if (doctor.hasAvailableCapacity(date)){
                if (doctor.isAvailableWithinShift(time)){
                    if (doctor.isAppointmentAvailable(date, time)){
                        Appointment appointment = new Appointment(id, patient, doctor, date, time);
                        appointments.add(appointment);
                        Collections.sort(appointments);

                        doctor.addAppointment(appointment);
                        Collections.sort(doctor.getAppointments());

                        patient.addAppointment(appointment);
                        Collections.sort(patient.getAppointments());

                        hospitalIncomes.add(new IncomeMedicalService(findMedicalServicebyId(1), patient));
                        patient.getBill().addCharge(new Charge(nextChargeId, findMedicalServicebyId(1), LocalDate.now()));
                        return appointment;
                    }
                    System.out.println("Appointment is full at time, date");
                    return null;
                }
                System.out.println("Out of doctors shift");
                return null;
            }
            System.out.println("Doctor is full");
            return null;
        }
        System.out.println("Doctor isn't in this ward");
        return null;
    }

    public void closeAppointment(Appointment appointment){
        appointment.setApStatus(AppointmentStatus.COMPLETED);
    }

    public void cancelAppointment(Appointment appointment){
        appointments.remove(appointment);
        appointment.getPatient().getAppointments().remove(appointment);
        appointment.getDoctor().getAppointments().remove(appointment);
    }

    // Medical Services Section

    public ArrayList<MedicalService> getMedicalServices() {return medicalServices;}

    public void addMedicalService(MedicalService service){
        medicalServices.add(service);
    }

    public MedicalService findMedicalServicebyId(int id){
        for (MedicalService ms: medicalServices){
            if (ms.getId() == id){
                return ms;
            }
        }
        return null;
    }

    // Budget

    public ArrayList<HospitalIncome> getHospitalIncomes() {return hospitalIncomes;}

    public double getTotalHospitalBudget(){
        double budget = 0;
        for (HospitalIncome income: hospitalIncomes){
            budget += income.getAmount();
        }

        return budget;
    }
}

