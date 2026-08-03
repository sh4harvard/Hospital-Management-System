package Hospital;

public class WardBonus implements HospitalIncome{
    private Ward ward;
    private double amount;

    public WardBonus(Ward ward) {
        this.ward = ward;
        this.amount = 500;
    }

    public Ward getWard() {
        return ward;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String showInfo() {
        return "Ward Emptied Bonus\t" + ward.getName() + "\t" + amount;
    }
}
