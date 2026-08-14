package Hospital.Core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Hospital.Core.enums.Gender;

public class Doctor extends Person implements DisplayInformation{

    private String specialty;
    private Ward ward;
    private int dailyCapacity;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    private ArrayList<Appointment> appointments;

    public Doctor(int id, String name, int age, Gender gender, String phoneNumber, String specialty, Ward ward, int dailyCapacity, LocalTime shiftStart, LocalTime shiftEnd){
        super(id, name, age, gender, phoneNumber);

        this.specialty = specialty;
        this.ward = ward;
        this.dailyCapacity = dailyCapacity;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        appointments = new ArrayList<>();
    }

    @Override
    public String showInfo() {
        return
            "===== Doctor's Information =====" +
            "\nID: " + getId() +
            "\nAge: " + getAge() +
            "\nName: " + getName() +
            "\nGender: " + getGender() +
            "\nPhone: " + getPhoneNumber() +
            "\nSpecialty: " + getSpecialty() +
            "\nWard: " + getWard() +
            "\ndailyCapacity: " + getDailyCapacity() +
            "\nshiftStart: " + getShiftStart() +
            "\nshiftEnd: " + getShiftEnd();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public int getDailyCapacity() {
        return dailyCapacity;
    }

    public void setDailyCapacity(int dailyCapacity) {
        this.dailyCapacity = dailyCapacity;
    }

    public boolean hasAvailableCapacity(LocalDate date) {
        int spaceAvailable = 0;

        for (Appointment ap : appointments) {
            if (ap.getDate().equals(date)) {
                spaceAvailable++;
            }
        }

        return spaceAvailable < dailyCapacity;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public ArrayList<Appointment> getAppointments(){ return appointments;}

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }

    public boolean isAvailableWithinShift(LocalTime time){
        return !(time.isBefore(shiftStart) || time.isAfter(shiftEnd));
    }

    public boolean isAppointmentAvailable(LocalDate date, LocalTime time){
        for (Appointment ap : appointments){
            if (ap.getDate().equals(date) && ap.getTime().equals(time)){
                return false;
            }
        }
        return true;
    }
}
