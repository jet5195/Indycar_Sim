/**
 * Created by xsc7043 on 9/22/2017.
 */
public class Driver {
    //forever stats
    private String firstName;
    private String lastName;
    private String nationality;
    private int marketability;
    private int qualify;
    private int street;
    private int road;
    private int oval;
    private int consistency;
    private int aggression;
    private int overall;
    private int potential;
    private int workEthic;
    private int DNFtendency;
    private int age;
    private int specialist;//0 means no, 1 means oval, 2 means road/street

    //Race Stats
    private int raceAbility = 0;
    //private int qualSpeed;
    //private int speed;
    private int lapsLed = 0;
    private boolean dnf = false;

    //Season Stats
    private int points = 0;
    private int seasonPoles = 0;
    private int seasonWins = 0;
    private int seasonLapsLed = 0;
    private int seasonPodiums = 0;
    private int seasonDnfs = 0;

    //Career Stats
    private int careerPoles = 0;
    private int championships = 0;
    private int careerWins = 0;
    private int careerLapsLed = 0;
    private int careerPodiums = 0;
    private int careerDnfs = 0;


    public Driver(String firstName, String lastName, String nationality, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.age = age;
    }

    public void setAttributes(int marketablity, int qualify, int street, int road, int oval, int consistency,
                              int aggression, int potential, int workEthic) {
        this.marketability = marketablity;
        this.qualify = qualify;
        this.street = street;
        this.road = road;
        this.oval = oval;
        this.consistency = consistency;
        this.aggression = aggression;
        this.potential = potential;
        this.workEthic = workEthic;
        this.DNFtendency = ((100-this.consistency)+ this.aggression)/2;
    }

    public int getSpecialist(){
        return specialist;
    }

    public void setSpecialist(){
        if (oval>(1.75*road)&&oval>(1.75*street)){
            specialist =1;
        } else if(road>(oval*1.75)&&street>(oval*1.75)){
            specialist = 2;
        } else{
            specialist = 0;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getMarketablity() {
        return marketability;
    }

    public void setMarketablity(int marketablity) {
        this.marketability = marketablity;
    }

    public int getStreet(){
        return street;
    }

    public void setStreet(int street){
        this.street = street;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public int getOval() {
        return oval;
    }

    public void setOval(int oval) {
        this.oval = oval;
    }

    public int getConsistency() {
        return consistency;
    }

    public void setConsistency(int consistency) {
        this.consistency = consistency;
    }

    public int getAggression() {
        return aggression;
    }

    public void setAggression(int aggression) {
        this.aggression = aggression;
    }

    public int getQualify() {
        return qualify;
    }

    public void setQualify(int qualify) {
        this.qualify = qualify;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public int getWorkEthic() {
        return workEthic;
    }

    public void setWorkEthic(int workEthic) {
        this.workEthic = workEthic;
    }

    public int getDNFtendency() {
        return DNFtendency;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLapsLed() {
        return lapsLed;
    }

    public void incrementLapsLed() {
        lapsLed++;
        seasonLapsLed++;
        careerLapsLed++;
    }

    public void resetLapsLed(){
        this.lapsLed = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int num) {
        points += num;
    }

    public void addWin() {
        seasonWins++;
        careerWins++;
    }

    public void addPodium() {
        seasonPodiums++;
        careerPodiums++;
    }

    public void addChampionship() {
        championships++;
    }

    public void addPole() {
        seasonPoles++;
        careerPoles++;
    }

    public void addDnf() {
        seasonDnfs++;
        careerDnfs++;
    }

    public int getSeasonWins() {
        return seasonWins;
    }

    public int getSeasonLapsLed() {
        return seasonLapsLed;
    }

    public int getSeasonPodiums() {
        return seasonPodiums;
    }

    public int getChampionships() {
        return championships;
    }

    public int getCareerWins() {
        return careerWins;
    }

    public int getCareerLapsLed() {
        return careerLapsLed;
    }

    public int getCareerPodiums() {
        return careerPodiums;
    }

    public int getSeasonPoles() {
        return seasonPoles;
    }

    public int getCareerPoles() {
        return careerPoles;
    }

    public boolean isDnf() {
        return dnf;
    }

    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }

    public int getSeasonDnf(){
        return seasonDnfs;
    }

    public int getCareerDnfs(){
        return careerDnfs;
    }



    public void endOfSeasonReset() {
        points = 0;
        seasonLapsLed = 0;
        seasonPodiums = 0;
        seasonWins = 0;
        seasonPoles = 0;
        seasonDnfs = 0;
    }

//    @Override
//    public int compareTo(Driver o) {
//        int compareSpeed = o.getSpeed();
//       return compareSpeed - this.speed;
//    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
