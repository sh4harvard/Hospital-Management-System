package Hospital.Core;

import java.time.LocalDate;

public interface HospitalIncome extends DisplayInformation{
    public String getType();
    public int getIncomeProperty();
    public String getName();
    public double getAmount();
    public LocalDate getDate();

    public void setAmount(double amount);
    public void setDate(LocalDate date);
}
