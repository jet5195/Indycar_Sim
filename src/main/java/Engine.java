public class Engine {
    private int id;
    private String name;
    private boolean active;
    private int power;
    private int economy;
    private int reliability;

    public Engine(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setAttributes(boolean active, int power, int economy, int reliability){
        this.power = power;
        this.economy = economy;
        this.reliability = reliability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getEconomy() {
        return economy;
    }

    public void setEconomy(int economy) {
        this.economy = economy;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }
}
