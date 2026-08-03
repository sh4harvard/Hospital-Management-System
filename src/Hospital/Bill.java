package Hospital;

import java.util.ArrayList;

public class Bill implements DisplayInformation{

    private ArrayList<Charge> charges;

    public Bill() {
        charges = new ArrayList<>();
    }

    public ArrayList<Charge> getCharges(){
        return charges;
    }

    public void addCharge(Charge charge){
        charges.add(charge);
    }

    public void removeCharge(Charge charge){
        charges.remove(charge);
    }

    public double getTotal(){
        double total = 0;
        for (Charge c:charges){
            total += c.getService().getCost();
        }
        return total;
    }

    public double getTotalPaid(){
        double total = 0;
        for (Charge c:charges){
            if (c.getPayStatus()) {
                total += c.getService().getCost();
            }
        }
        return total;
    }

    public double getTotalUnpaid(){
        double total = 0;
        for (Charge c:charges){
            if (!c.getPayStatus()) {
                total += c.getService().getCost();
            }
        }
        return total;
    }

    @Override
    public String showInfo() {
        if (charges.isEmpty()) {
            return "No charges.";
        }

        String result = "";

        result += "========== BILL ==========" +
            "\n--------------------------\n";

        for (Charge c:charges){
            result += c.showInfo();
        }

        result += "-------------------------\n" +
        "\nTotal Paid:  T" + getTotalPaid() +
        "\nTotal Remaining:  T" + getTotalUnpaid() +
        "\nGrand Total:  T" + getTotal();

        return result;
    }
}
