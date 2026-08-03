package Hospital;

public class MedicalService implements DisplayInformation{
    private final int id;
    private String name;
    private double cost;

    public MedicalService(int id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String showInfo() {
        return id + "    " + name + "    " + cost;
    }
}
