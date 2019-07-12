import java.util.Random;

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
    private EntryType entryType;//0 is full, 1 is half, 2 is road/street, 3 is just Ovals, 4 is just May, 5 is just Indy 500
    private double speed;//used as laptime for qualifying session, then total time for race sessions
    //private double qualifyTime;
    private double raceAbility;
    private int smallOval;
    private int largeOval;
    private int road;
    private int street;
    private int pitCrew;
    private int position;

    //inherit stuff from team and combine it with driver values to get race values! Use them here. Switch a lot
    //of stuff from the driver class to here that say whether or not a driver does well in a race
    //also need to keep track of what team it is a part of. Maybe make it so the car keeps track of what team it is
    //instead of the team keeping track of its cars?


    public Car(Driver driver, String number, String team, EntryType entryType, int smallOval, int largeOval, int road, int street, int pitCrew){
        this.driver = driver;
        this.number = number;
        this.team = team;
        this.entryType = entryType;
        this.smallOval = smallOval;
        this.largeOval = largeOval;
        this.road = road;
        this.street = street;
        this.pitCrew = pitCrew;
    }

    public int getSmallOval() {
        return smallOval;
    }

    public void setSmallOval(int smallOval) {
        this.smallOval = smallOval;
    }

    public int getLargeOval() {
        return largeOval;
    }

    public void setLargeOval(int largeOval) {
        this.largeOval = largeOval;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
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

    public double calculateLapTime(Race race, LapType lapType){
        //optimization is a double with a number somewhere around 1
        double lapTime = race.getTrack().getLapTime();
        int min =  998000;
        int max = 1002000;
        Random r = new Random();
        double randomFactor = ((double)r.ints(1, min, max).findFirst().getAsInt())/1000000;
        //System.out.println("random factor is: " + randomFactor + ", RaceAbility is: " + raceAbility);
        if (lapType == LapType.QUALIFY){

        }
        return lapTime*randomFactor * this.getRaceAbility();
    }

    public double calculateMPH(Race race, LapType lapType){
        double lapTime = this.calculateLapTime(race, lapType);
        return calculateMPH(lapTime, race.getTrack());
    }

    public double calculateMPH(double lapTime, Track track){
        return (track.getMiles()/lapTime)*3600;
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

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

/*    public double getQualifyTime() {
        return qualifyTime;
    }

    public void setQualifyTime(double qualifyTime) {
        this.qualifyTime = qualifyTime;
    }*/

    public double getRaceAbility() {
        return raceAbility;
    }

    public void setRaceAbility(Race race) {//call this once before a race
        int carAbility = 0;
        int driverAbility = 0;
        if (race.getType()==TrackType.STREET_COURSE){
            carAbility = this.getStreet();
            driverAbility = this.getDriver().getStreet();
        }
        else if (race.getType()==TrackType.ROAD_COURSE){
            carAbility = this.getRoad();
            driverAbility = this.getDriver().getRoad();
        }
        else if (race.getType()==TrackType.SMALL_OVAL){
            carAbility = this.getSmallOval();
            driverAbility = this.getDriver().getSmallOval();
        }
        else if (race.getType()==TrackType.LARGE_OVAL){
            carAbility = this.getLargeOval();
            driverAbility = this.getDriver().getLargeOval();
        }
        double newConsistency = (double) this.getDriver().getConsistency() / 2 + 25;
        double inverseCon = 100 - newConsistency;
        double subtract = (Math.random() * inverseCon);
        driverAbility -= subtract;
        //System.out.print("driverAbility: " + driverAbility + ", carAbility: " + carAbility +  " = ");
        //double consistency = this.getDriver().getConsistency();
        this.raceAbility = driverAbility + carAbility*.50;
        //System.out.println(ability);
        this.raceAbility = (this.raceAbility+4000)/4075;
        this.raceAbility = (this.raceAbility* -1) + 2;//before this, a higher number means you're faster. Which isn't useful if we're looking at laptimes, so this inverses it.
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

    public void doLap(Race race, LapType lapType) {
        this.speed += this.calculateLapTime(race, lapType);
    }

    //@Override
    public int compareTo(Car o) {
        double compareSpeed = o.getSpeed();
        return (int)(this.speed*1000 - compareSpeed*1000);
    }

}
