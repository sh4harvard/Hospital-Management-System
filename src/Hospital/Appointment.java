package Hospital;

import java.time.*;
import Hospital.enums.AppointmentStatus;

public class Appointment implements DisplayInformation{

    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus apStatus;

    public Appointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time){
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        apStatus = AppointmentStatus.SCHEDULED;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void showInfo(){
        System.out.println(
                "===== Appointment's Information =====" +
                "\nPatient: " + patient +
                "\nDoctor: " + doctor +
                "\ndate " + date +
                "\nTime " + time
        );
    }

    public AppointmentStatus getApStatus() {
        return apStatus;
    }

    public void setApStatus(AppointmentStatus apStatus) {
        this.apStatus = apStatus;
    }
}
