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

    private String lastAppointmentError;


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

    public boolean admitPatient(Patient patient, Ward ward) {

        if (ward.addPatient(patient)) {
            return true;
        }

        return false;
    }

    public void dischargePatient(Patient patient) {

        Ward ward = patient.getWard();

        if (ward != null) {

            ward.removePatient(patient);

            if (ward.getPatients().isEmpty()) {
                hospitalIncomes.add(new WardBonus(ward));
            }
        }
    }

    public void deletePatient(Patient patient) {


        if (patient.getWard() != null) {
            dischargePatient(patient);
        }

        ArrayList<Appointment> patientAppointments =
                new ArrayList<>(patient.getAppointments());

        for (Appointment appointment : patientAppointments) {
            cancelAppointment(appointment);
        }

        patients.remove(patient);
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

    public void removeDoctor(Doctor doctor) {

        // Remove  doctor ward
        if (doctor.getWard() != null) {
            doctor.getWard().removeDoctor(doctor);
        }

        // Remove appointments
        ArrayList<Appointment> doctorAppointments =
                new ArrayList<>(doctor.getAppointments());

        for (Appointment appointment : doctorAppointments) {
            cancelAppointment(appointment);
        }

        // Remove doctor from hospital
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

    public boolean transferWardPatient(Patient patient, Ward newWard) {

        if (patient.getWard() == newWard) {
            return true;
        }

        if (newWard != null &&
                newWard.getPatients().size() >= newWard.getCapacity()) {
            return false;
        }

        Ward oldWard = patient.getWard();

        if (oldWard != null) {
            oldWard.removePatient(patient);

            if (oldWard.getPatients().isEmpty()
                    && findWardBonusByWardId(oldWard.getId()) == null) {

                hospitalIncomes.add(new WardBonus(oldWard));
            }
        }

        if (newWard != null) {
            newWard.addPatient(patient);
        }

        return true;
    }

    public boolean transferWardDoctor(Doctor doctor, Ward newWard) {

        if (doctor.getWard() == newWard) {
            return true;
        }

        Ward oldWard = doctor.getWard();

        if (oldWard != null) {
            oldWard.removeDoctor(doctor);

            if (oldWard.getDoctors().isEmpty()
                    && findWardBonusByWardId(oldWard.getId()) == null) {

                hospitalIncomes.add(new WardBonus(oldWard));
            }
        }

        if (newWard != null) {
            newWard.addDoctor(doctor);
        }

        return true;
    }

    // Appointment Section

    public ArrayList<Appointment> getAppointments() {return appointments;}

    public Appointment createAppointment(
            int id,
            Patient patient,
            Doctor doctor,
            LocalDate date,
            LocalTime time) {

        lastAppointmentError = null;

        if (patient.getWard() != null
                && patient.getWard() != doctor.getWard()) {

            lastAppointmentError =
                    "Doctor isn't in the patient's ward.";

            return null;
        }

        if (!doctor.hasAvailableCapacity(date)) {

            lastAppointmentError =
                    "Doctor is full on this date.";

            return null;
        }

        if (!doctor.isAvailableWithinShift(time)) {

            lastAppointmentError =
                    "The selected time is outside the doctor's shift.";

            return null;
        }

        if (!doctor.isAppointmentAvailable(date, time)) {

            lastAppointmentError =
                    "The doctor already has an appointment at this time.";

            return null;
        }

        Appointment appointment =
                new Appointment(
                        id,
                        patient,
                        doctor,
                        date,
                        time
                );

        appointments.add(appointment);

        Collections.sort(appointments);

        doctor.addAppointment(appointment);

        Collections.sort(
                doctor.getAppointments()
        );

        patient.addAppointment(appointment);

        Collections.sort(
                patient.getAppointments()
        );

        hospitalIncomes.add(
                new IncomeMedicalService(
                        findMedicalServicebyId(1),
                        patient
                )
        );

        patient.getBill().addCharge(
                new Charge(
                        generateChargeId(),
                        findMedicalServicebyId(1),
                        LocalDate.now()
                )
        );

        return appointment;
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

    public MedicalService findMedicalServicebyName(String name){
        for (MedicalService ms: medicalServices){
            if (ms.getName().equals(name)){
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

    public WardBonus findWardBonusByWardId(int id){
        for (HospitalIncome income: hospitalIncomes){
            if (income.getType().equals("WARD_BONUS") && income.getIncomeProperty() == id){
                return (WardBonus) income;
            }
        }

        return null;
    }

    public String getLastAppointmentError() {
        return lastAppointmentError;
    }
}

