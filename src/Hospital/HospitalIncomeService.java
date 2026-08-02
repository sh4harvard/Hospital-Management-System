package Hospital;

public class HospitalIncomeService implements DisplayInformation{

    private MedicalService medicalService;
    private Patient patient;
    private double amount;

    public HospitalIncomeService(MedicalService medicalService, Patient patient) {
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
    public void showInfo() {
        System.out.println(medicalService.getId() + "\t" + medicalService.getName() +"\t"+ medicalService.getCost()
                +"\t"+ patient.getId() +"\t"+ patient.getName());
    }
}
