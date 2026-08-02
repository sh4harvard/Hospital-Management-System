package Hospital;

import java.time.LocalDate;

public class MedicalRecord implements DisplayInformation{

    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDate lastDiagnoseDate;

    public void setMRecord (String diagnosis, String prescription, String notes, LocalDate lastDiagnoseDate) {
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.lastDiagnoseDate = lastDiagnoseDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getLastDiagnoseDate() {
        return lastDiagnoseDate;
    }

    public void setLastDiagnoseDate(LocalDate lastDiagnoseDate) {
        this.lastDiagnoseDate = lastDiagnoseDate;
    }

    public void showInfo(){
        System.out.println(
            "===== Patient's Medical Record =====" +
            "\nDiagnosis: " + diagnosis +
            "\nPrescription: " + prescription +
            "\nNotes: " + notes +
            "\nLast Diagnosis Date: " + lastDiagnoseDate
        );
    }
}
