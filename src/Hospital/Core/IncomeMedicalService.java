package Hospital.Core;

public class IncomeMedicalService implements HospitalIncome {

    private MedicalService medicalService;
    private Patient patient;
    private double amount;

    public IncomeMedicalService(MedicalService medicalService, Patient patient) {
        this.medicalService = medicalService;
        this.patient = patient;
        amount = medicalService.getCost();
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public Patient getPatient() {
        return patient;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String showInfo() {
        return medicalService.getId() + "\t" + medicalService.getName() +"\t"+ medicalService.getCost()
                +"\t"+ patient.getId() +"\t"+ patient.getName();
    }
}
