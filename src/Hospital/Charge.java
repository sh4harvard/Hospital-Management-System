package Hospital;

public class Charge implements DisplayInformation{

    private MedicalService service;
    private boolean payStatus;

    public Charge(MedicalService service) {
        this.service = service;
        payStatus = false;
    }

    public MedicalService getService() {
        return service;
    }

    public boolean getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(boolean payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String showInfo() {
        return service.getName() + "    " + service.getCost() + "T    " + ((payStatus) ? "Paid" : "");
    }
}
