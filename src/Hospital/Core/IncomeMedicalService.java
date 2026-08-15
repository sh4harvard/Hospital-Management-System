package Hospital.Core;

import java.time.LocalDate;

public class IncomeMedicalService implements HospitalIncome {

    private MedicalService medicalService;
    private Patient patient;
    private double amount;
    private LocalDate date;

    public IncomeMedicalService(MedicalService medicalService, Patient patient) {
        this.medicalService = medicalService;
        this.patient = patient;
        amount = medicalService.getCost();
        date = LocalDate.now();
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date){this.date = date;}

    @Override
    public String getType(){return "MEDICAL_SERVICE";};
    public int getIncomeProperty(){return patient.getId();};
    public String getName(){return medicalService.getName();};
    public double getAmount(){return amount;};
    public LocalDate getDate(){return date;};

    @Override
    public String showInfo() {
        return medicalService.getId() + "\t" + medicalService.getName() +"\t"+ medicalService.getCost()
                +"\t"+ patient.getId() +"\t"+ patient.getName();
    }
}
