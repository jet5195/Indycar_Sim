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

    //inherit stuff from team and combine it with driver values to get race values! Use them here. Switch a lot
    //of stuff from the driver class to here that say whether or not a driver does well in a race
    //also need to keep track of what team it is a part of. Maybe make it so the car keeps track of what team it is
    //instead of the team keeping track of its cars?

}
