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
            System.out.println("No charges.");
            return;
        }



        System.out.println(
            "========== BILL ==========" +
            "\n--------------------------\n"
        );

        for (Charge c:charges){
            c.showInfo();
        }

        System.out.println("-------------------------\n");
        System.out.println("Total Paid:  T" + getTotalPaid());
        System.out.println("Total Remaining:  T" + getTotalUnpaid());
        System.out.println("Grand Total:  T" + getTotal());
    }
}
