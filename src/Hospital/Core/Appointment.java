package Hospital.Core;

import java.time.*;
import Hospital.Core.enums.AppointmentStatus;

public class Appointment implements DisplayInformation, Comparable<Appointment>{

    private final int id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus apStatus;

    public Appointment(int id, Patient patient, Doctor doctor, LocalDate date, LocalTime time){
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        apStatus = AppointmentStatus.SCHEDULED;
    }

    public int getId() {return id;}

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

    public String showInfo(){
        return
            "===== Appointment's Information =====" +
            "\nPatient: " + patient +
            "\nDoctor: " + doctor +
            "\ndate " + date +
            "\nTime " + time;
    }

    public AppointmentStatus getApStatus() {
        return apStatus;
    }

    public void setApStatus(AppointmentStatus apStatus) {
        this.apStatus = apStatus;
    }

    // Sort
    @Override
    public int compareTo(Appointment other) {
        int result = this.date.compareTo(other.date);

        if(result != 0){
            return result;
        }
        return this.time.compareTo(other.time);
    }
}
