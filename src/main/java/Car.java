public class Car implements Comparable<Car>{
    private static int horsepower = 700;
    private static int torque = 500;
    private static double mpg = 1.98;
    private static double fuelCapacity = 18.5; //gallons
    private static double weight = 1600; //lbs
    private String number;
    private Driver driver;
    private String team;
    private double fuel;
    private static final double fuelWeight = 6.7; //lbs per gallon
    private int tireLife;
    private int tireAge;
    private int tireType;
    private int lastPit;
    private int fullTime;//0 is full, 1 is half, 2 is road/street, 3 is just Ovals, 4 is just May, 5 is just Indy 500
    private int speed;
    private int qualSpeed;
    private double raceAbility;
    private int oval;
    private int road;
    private int pitCrew;
    private int position;

    //inherit stuff from team and combine it with driver values to get race values! Use them here. Switch a lot
    //of stuff from the driver class to here that say whether or not a driver does well in a race
    //also need to keep track of what team it is a part of. Maybe make it so the car keeps track of what team it is
    //instead of the team keeping track of its cars?


    public Car(Driver driver, String number, String team, int fullTime, int oval, int road, int pitCrew){
        this.driver = driver;
        this.number = number;
        this.team = team;
        this.fullTime = fullTime;
        this.oval = oval;
        this.road = road;
        this.pitCrew = pitCrew;
    }

    public int getOval() {
        return oval;
    }

    public void setOval(int oval) {
        this.oval = oval;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public int getPitCrew() {
        return pitCrew;
    }

    public void setPitCrew(int pitCrew) {
        this.pitCrew = pitCrew;
    }

    public static int getHorsepower() {
        return horsepower;
    }

    public static int getTorque() {
        return torque;
    }

    public static double getMpg() {
        return mpg;
    }

    public static double getFuelCapacity() {
        return fuelCapacity;
    }

    public static double getWeight() {
        return weight;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getFuel() {
        return fuel;
    }

    public void addFuel(double fuel) {
        if(fuel + this.fuel> fuelCapacity){
            this.fuel = fuelCapacity;
        }else {
            this.fuel += fuel;
        }
    }

    public void decreaseFuel(double miles, int mode){//mode 1 is caution, 2 is conserve, 3 is normal, 4 is push
        double modeCoefficient = 0;
        switch(mode){
            case 1:
                modeCoefficient = 2;
                break;
            case 2:
                modeCoefficient = 1.25;
                break;
            case 3:
                modeCoefficient = 1;
                break;
            case 4:
                modeCoefficient = .75;
                break;
                default:
        }
        double currentMPG = modeCoefficient*this.mpg;
        double fuelLost = miles/(currentMPG);
        this.fuel-=fuelLost;
        this.weight-=(fuelLost*fuelWeight);
    }

    public int getTireLife() {
        return tireLife;
    }

    public void changeTires(int type) {//blacks are 1, reds 2, rain 3 lolz
        tireLife = 100;
        tireType = type;
    }

    public int getTireAge() {
        return tireAge;
    }

    public void setTireAge(int tireAge) {
        this.tireAge = tireAge;
    }

    public int getLastPit() {
        return lastPit;
    }

    public void setLastPit(int lastPit) {
        this.lastPit = lastPit;
    }

    public int getFullTime() {
        return fullTime;
    }

    public void setFullTime(int fullTime) {
        this.fullTime = fullTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getQualSpeed() {
        return qualSpeed;
    }

    public void setQualSpeed(int qualSpeed) {
        this.qualSpeed = qualSpeed;
    }

    public double getRaceAbility() {
        return raceAbility;
    }

    public void setRaceAbility(double raceAbility) {
        this.raceAbility = raceAbility;
    }

    public void endOfRaceReset() {
        speed = 0;
        driver.resetLapsLed();
        raceAbility = 0;
        driver.setDnf(false);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(Car o) {
        int compareSpeed = o.getSpeed();
        return compareSpeed - this.speed;
    }

}
