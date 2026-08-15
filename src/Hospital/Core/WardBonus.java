package Hospital.Core;

import java.time.LocalDate;

public class WardBonus implements HospitalIncome{
    private Ward ward;
    private double amount;
    private LocalDate date;

    public WardBonus(Ward ward) {
        this.ward = ward;
        amount = 500; // PDF
        date = LocalDate.now();
    }

    public Ward getWard() {
        return ward;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date){this.date = date;}


    @Override
    public String getType(){return "WARD_BONUS";};
    public int getIncomeProperty(){return ward.getId();};
    public String getName(){return ward.getName()+" Bonus";};
    public double getAmount(){return amount;};
    public LocalDate getDate(){return date;};

    @Override
    public String showInfo() {
        return "Ward Emptied Bonus\t" + ward.getName() + "\t" + amount;
    }
}
