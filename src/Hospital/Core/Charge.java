package Hospital.Core;

import java.time.LocalDate;

public class Charge implements DisplayInformation{

    private final int id;
    private MedicalService service;
    private boolean payStatus;
    private LocalDate date;

    public Charge(int id, MedicalService service, LocalDate date) {
        this.id = id;
        this.service = service;
        payStatus = false;
        this.date = date;
    }

    public int getId() {return id;}

    public MedicalService getService() {
        return service;
    }

    public boolean getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(boolean payStatus) {
        this.payStatus = payStatus;
    }

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    @Override
    public String showInfo() {
        return service.getName() + "    " + service.getCost() + "T    " + ((payStatus) ? "Paid" : "");
    }
}
