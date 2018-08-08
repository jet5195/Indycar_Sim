/**
 * Created by xsc7043 on 3/27/2018.
 */
public class Race {
    private Track track;
    private String raceTitle;
    private double totalMiles;
    private boolean doublePoints = false;
    int laps;
    private int prestige;//10 is best, most races will be under 5. ie indy 500 is 10, monaco is 10, daytona is 9,

    public Race(Track track, String raceTitle, int laps, int prestige) {
        this.raceTitle = raceTitle;
        this.laps = laps;
        this.totalMiles = laps * track.getMiles();
        this.track = track;
        this.prestige = prestige;
    }


    public String getRaceTitle() {
        return raceTitle;
    }

    public void setRaceTitle(String raceTitle) {
        this.raceTitle = raceTitle;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public double getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(double totalMiles) {
        this.totalMiles = totalMiles;
    }

    public String getName() {
        return track.getName();
    }

    public String getType() {
        return track.getType();
    }

    public double getMiles() {
        return track.getMiles();
    }

    public int getTurns() {
        return track.getTurns();
    }

    public String getCountry() {
        return track.getCountry();
    }

    public String getState() {
        return track.getState();
    }

    public String getCity() {
        return track.getCity();
    }

    public boolean isDoublePoints() {
        return doublePoints;
    }

    public void setDoublePoints(boolean doublePoints) {
        this.doublePoints = doublePoints;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    @Override
    public String toString() {
        return raceTitle;
    }
}
