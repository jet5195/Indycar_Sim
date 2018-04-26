public class Car {
    private String number;
    private Driver driver;
    private String team;
    private int fuel;
    private int tireLife;
    private int tireAge;
    private int lastPit;
    private int fullTime;//0 is full, 1 is half, 2 is just May, 3 is just Indy 500

    public Car(Driver driver, String number, String team, int fullTime){
        this.driver = driver;
        this.number = number;
        this.team = team;
        this.fullTime = fullTime;
    }

}
