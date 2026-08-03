package Hospital;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import Hospital.enums.AppointmentStatus;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ArrayList<Ward> wards;
    private ArrayList<Appointment> appointments;
    private ArrayList<MedicalService> medicalServices;
    private ArrayList<HospitalIncome> hospitalIncomes;

    public HospitalSystem(){
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        wards = new ArrayList<>();
        appointments = new ArrayList<>();
        medicalServices = new ArrayList<>();
        hospitalIncomes = new ArrayList<>();

        // TODO: DB for medical services cost
        MedicalService appBooking = new MedicalService(1, "Booking Appointment", 100);
        medicalServices.add(appBooking);
        Ward emergency = new Ward("Emergency", 200);
        wards.add(emergency);
    }

    // Patient Section
    public void addPatient(Patient patient){
        patients.add(patient);
    }

    public void removePatient(Patient patient){
        patients.remove(patient);
    }

    // findPatientById(String id)

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

    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }

    //findDoctorById(String id)


    // Ward Section
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
    public void createAppointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time){
        if (patient.getWard() == null || patient.getWard() == doctor.getWard()){
            if (doctor.hasAvailableCapacity(date)){
                if (doctor.isAvailableWithinShift(time)){
                    if (doctor.isAppointmentAvailable(date, time)){
                        Appointment appointment = new Appointment(patient, doctor, date, time);
                        appointments.add(appointment);
                        Collections.sort(appointments);

                        doctor.addAppointment(appointment);
                        Collections.sort(doctor.getAppointments());

                        patient.addAppointment(appointment);
                        Collections.sort(patient.getAppointments());

                        hospitalIncomes.add(new IncomeMedicalService(findMedicalServicebyId(1), patient));
                        patient.getBill().addCharge(new Charge(findMedicalServicebyId(1)));
                        return;
                    }
                    System.out.println("Appointment is full at time, date");
                    return;
                }
                System.out.println("Out of doctors shift");
                return;
            }
            System.out.println("Doctor is full");
            return;
        }
        System.out.println("Doctor isn't in this ward");
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
    public double getTotalHospitalBudget(){
        double budget = 0;
        for (HospitalIncome income: hospitalIncomes){
            budget += income.getAmount();
        }

        return budget;
    }
}

