public class Manufacturer {
    private String name;
    private int power;
    private int economy;
    private int reliability;

    public Manufacturer(String name){
        this.name = name;
    }

    public void setAttributes(int power, int economy, int reliability){
        this.power = power;
        this.economy = economy;
        this.reliability = reliability;
    }
}
