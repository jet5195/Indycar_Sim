import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsc7043 on 9/25/2017.
 */
public class Track {
    private String name;

    private String type;
    private double miles;
    private int laps;

    private int turns;
    private String country;
    private String state;
    private String city;

    public Track(String name, String type, double miles, int turns, String country, String state, String city) {
        this.name = name;
        this.type = type;
        this.miles = miles;
        this.country = country;
        this.state = state;
        this.city = city;
        this.turns = turns;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double lapTime(){
        double mph = 250;//lolz this is the average speed; maybe make it different for oval and rc/street
        //mph-=5*theRace.getTurns();
        if(this.getType()!="Oval"){
            mph-=15*this.getMiles();
            if (this.getType()=="Street"){
                mph-=10;
            }
        }
        double turnsMilesRatio = this.getTurns()/this.getMiles();
        mph-=turnsMilesRatio*15;
        double mps = mph/60/60;
        double lapTime = this.getMiles()/mps;
        //System.out.println(lapTime);
        return lapTime;

        //returns the fastest possible laptime for a track! Doesn't do too poorly
    }


}
