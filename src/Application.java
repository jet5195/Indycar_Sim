import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Application {

    static List<Driver> allDriverList = new ArrayList<>();
    static List<Track> allTrackList = new ArrayList<>();
    static List<Driver> activeDriverList = new ArrayList<>();
    static List<Race> activeRaceList = new ArrayList<>();
    static List<Car> allCarList = new ArrayList<>();
    static List<Car> activeCarList = new ArrayList<>();
    static List<Team> allTeamList = new ArrayList<>();
    static List<Team> activeTeamList = new ArrayList<>();
    static List<Sponsor> allSponsorList = new ArrayList<>();
    static List<Manufacturer> allManufacturerList = new ArrayList<>();
    static List<Manufacturer> activeManufacturerList = new ArrayList<>();

    public static void main(String[] args) {

        setUp();
        menu();
        //qualify(activeRaceList.get(1));
    }

    public static void setUp() {
        existingDrivers();
        existingTracks();
    }

    public static void menu() {
        Scanner scan = new Scanner(System.in);
        boolean go = true;
        while (go) {
            System.out.println("Main Menu");
            System.out.println("____________");
            System.out.println("1. Sim Race");
            System.out.println("2. Season Mode");
            System.out.println("3. Career Mode");
            System.out.println("4. Exit");
            System.out.println("More Options Coming...");
            int input = scan.nextInt();

            switch (input) {
                case 1:
                    raceMenu(scan);
                    break;
                case 2:
                    seasonMenu(scan);
                    break;
                case 3:
                    careerMenu(scan);
                    break;
                case 4:
                    scan.close();
                    go = false;
                    break;
                default:
                    System.out.println("Try entering a different number");
                    break;
            }
        }
    }

    public static void careerMenu(Scanner scan) {
        System.out.println("Sorry, this mode is still under development");
    }

    public static void seasonMenu(Scanner scan) {
        for (int i = 0; i < activeDriverList.size(); i++) {
            activeDriverList.get(i).endOfSeasonReset();

        }
        //System.out.println("Sorry, this mode is still under development");
        int raceNum = 0;
        boolean go = true;
        while (go) {
            sortStandings();
            System.out.println("\nSeason Mode");
            System.out.println("____________________");
            if (raceNum < activeRaceList.size()) {
                System.out.println("1. Next Race");
            } else {
                System.out.println("1. Finish Season");
            }
            System.out.println("2. View Standings");
            System.out.println("3. View Season Stats");
            System.out.println("4. View Career Stats");
            System.out.println("5. Exit");
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    if (raceNum < activeRaceList.size()) {
                        qualify(activeRaceList.get(raceNum), 1);
                        raceNum++;
                    } else {
                        printStandings();
                        go = false;
                    }
                    break;
                case 2:
                    printStandings();
                    break;
                case 3:
                    viewSeasonStats();
                    break;
                case 4:
                    viewCareerStats();
                    break;
                case 5:
                    go = false;
                    break;
                default:
                    System.out.println("Try entering a different number");
                    break;
            }
        }

    }

    public static void viewSeasonStats() {
        System.out.println("Season Stats");
        System.out.printf("%-30s %-8s %-8s %-8s %-8s %-8s %-8s\n", "Driver", "Points", "Wins", "Poles", "Podiums", "Laps Led", "DNFs");
        System.out.println("_______________________________________________________________________");
        for (int i = 0; i < activeDriverList.size(); i++) {
            Driver theDriver = activeDriverList.get(i);
            System.out.printf("%-30s %-8d %-8d %-8d %-8d %-8d %-8d\n", theDriver, theDriver.getPoints(), theDriver.getSeasonWins(),
                    theDriver.getSeasonPoles(), theDriver.getSeasonPodiums(), theDriver.getSeasonLapsLed(), theDriver.getSeasonDnf());
        }
    }

    public static void viewCareerStats() {
        System.out.println("Career Stats");
        System.out.printf("%-30s %-8s %-8s %-8s %-8s %-8s %-8s\n", "Driver", "Champ", "Wins", "Poles", "Podiums", "Laps Led", "DNFs");
        System.out.println("_______________________________________________________________________");
        for (int i = 0; i < activeDriverList.size(); i++) {
            Driver theDriver = activeDriverList.get(i);
            System.out.printf("%-30s %-8d %-8d %-8d %-8d %-8d %-8d\n", theDriver, theDriver.getChampionships(), theDriver.getCareerWins(),
                    theDriver.getCareerPoles(), theDriver.getCareerPodiums(), theDriver.getCareerLapsLed(), theDriver.getCareerDnfs());
        }
    }

    public static void printStandings() {
        sortStandings();
        for (int i = 0; i < activeDriverList.size(); i++) {
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %d\n", start + activeDriverList.get(i).getFirstName() + " " + activeDriverList.get(i).getLastName(), activeDriverList.get(i).getPoints());
        }
    }

    public static void sortStandings() {
        for (int i = 0; i < activeDriverList.size(); i++) {
            int topPoints = activeDriverList.get(i).getPoints();
            int topPointsIndex = i;
            for (int j = i + 1; j < activeDriverList.size(); j++) {
                if (activeDriverList.get(j).getPoints() > topPoints) {
                    topPoints = activeDriverList.get(j).getPoints();
                    topPointsIndex = j;
                }
            }
            if (topPointsIndex != i) {
                Driver temp = activeDriverList.get(i);
                activeDriverList.set(i, activeDriverList.get(topPointsIndex));
                activeDriverList.set(topPointsIndex, temp);
            }
        }
    }

    public static void indycar2018points(Race theRace) {
        int mostLapsLed = 0;
        int mostLapsLedIndex = 0;
        int drconstant = 1;
        if(theRace.isDoublePoints()==true){
            drconstant=2;
        }
        for (int pos = 0; pos < activeDriverList.size(); pos++) {
            int driverLapsLed = activeDriverList.get(pos).getLapsLed();
            if (driverLapsLed > mostLapsLed) {
                mostLapsLed = driverLapsLed;
                mostLapsLedIndex = pos;
            }
            if (driverLapsLed > 0) {
                activeDriverList.get(pos).addPoints(1);
            }
            switch (pos) {
                case 0:
                    activeDriverList.get(pos).addPoints(50*drconstant);
                    break;
                case 1:
                    activeDriverList.get(pos).addPoints(40*drconstant);
                    break;
                case 2:
                    activeDriverList.get(pos).addPoints(35*drconstant);
                    break;
                case 3:
                    activeDriverList.get(pos).addPoints(32*drconstant);
                    break;
                case 4:
                    activeDriverList.get(pos).addPoints(30*drconstant);
                    break;
                case 5:
                    activeDriverList.get(pos).addPoints(28*drconstant);
                    break;
                case 6:
                    activeDriverList.get(pos).addPoints(26*drconstant);
                    break;
                case 7:
                    activeDriverList.get(pos).addPoints(24*drconstant);
                    break;
                case 8:
                    activeDriverList.get(pos).addPoints(22*drconstant);
                    break;
                case 9:
                    activeDriverList.get(pos).addPoints(20*drconstant);
                    break;
                case 10:
                    activeDriverList.get(pos).addPoints(19*drconstant);
                    break;
                case 11:
                    activeDriverList.get(pos).addPoints(18*drconstant);
                    break;
                case 12:
                    activeDriverList.get(pos).addPoints(17*drconstant);
                    break;
                case 13:
                    activeDriverList.get(pos).addPoints(16*drconstant);
                    break;
                case 14:
                    activeDriverList.get(pos).addPoints(15*drconstant);
                    break;
                case 15:
                    activeDriverList.get(pos).addPoints(14*drconstant);
                    break;
                case 16:
                    activeDriverList.get(pos).addPoints(13*drconstant);
                    break;
                case 17:
                    activeDriverList.get(pos).addPoints(12*drconstant);
                    break;
                case 18:
                    activeDriverList.get(pos).addPoints(11*drconstant);
                    break;
                case 19:
                    activeDriverList.get(pos).addPoints(10*drconstant);
                    break;
                case 20:
                    activeDriverList.get(pos).addPoints(9*drconstant);
                    break;
                case 21:
                    activeDriverList.get(pos).addPoints(8*drconstant);
                    break;
                case 22:
                    activeDriverList.get(pos).addPoints(7*drconstant);
                    break;
                case 23:
                    activeDriverList.get(pos).addPoints(6*drconstant);
                    break;
                default:
                    activeDriverList.get(pos).addPoints(5*drconstant);
                    break;
            }
            activeDriverList.get(pos).endOfRaceReset();
        }
        activeDriverList.get(mostLapsLedIndex).addPoints(2);
    }

    public static void raceMenu(Scanner scan) {
        boolean go = true;
        while (go) {
            System.out.println("Race Menu");
            System.out.println("_____________");
            System.out.println("Choose an Event");
            int i = 0;
            for (i = 0; i < activeRaceList.size(); i++) {
                System.out.println(i + 1 + ". " + activeRaceList.get(i));

            }
            System.out.println(++i + ". Back");
            int input = scan.nextInt();

            if (input <= activeRaceList.size() && input > 0) {
                qualify(activeRaceList.get(input - 1), 1);
            } else if (input == i) {
                go = false;
            } else {
                System.out.println("Try entering a different number");
            }
        }
    }

    public static void randomRace() {

    }

    public static void qualify(Race theRace, int mode) {
        double speedcalc = 0;
        System.out.println(theRace.getRaceTitle() + " Qualifying Results");
        System.out.println("__________________________________");

        for (int i = 0; i < activeDriverList.size(); i++) {
            Driver theDriver = activeDriverList.get(i);
            int inverseCon = 225 - theDriver.getConsistency();
            double rand = Math.random() * inverseCon;
            double aggBonus = Math.random() * (theDriver.getAggression() / 10);

            if (theRace.getType().equals("RC") || theRace.getType().equals("Street")) {
                speedcalc = theDriver.getRoad() - rand + aggBonus;
            } else if (theRace.getType().equals("Oval")) {
                speedcalc = theDriver.getOval() - rand + aggBonus;
            }
            theDriver.setSpeed((int) speedcalc + 120);
            activeDriverList.set(i, theDriver);
        }

        Collections.sort(activeDriverList);
        if(theRace.toString().equals("Indianapolis 500")){
            int j = 0;
            for (int i = 9; i > 0; i--) {
                activeDriverList.get(j).addPoints(i);
                j++;
            }
        } else {
            activeDriverList.get(0).addPoints(1);
        }
        activeDriverList.get(0).addPole();

        for (int i = 0; i < activeDriverList.size(); i++) {
            activeDriverList.get(i).setQualSpeed(activeDriverList.get(i).getSpeed());
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %d\n", start + activeDriverList.get(i).getFirstName() + " " + activeDriverList.get(i).getLastName(), activeDriverList.get(i).getSpeed());

        }

        race(theRace, mode);

    }

    public static double createSpeed(Driver theDriver, Race theRace) {
        int speedConstant = 1800;
        double ability = theDriver.getRaceAbility();
        double speedcalc = 0;
        int inverseCon = 75 - theDriver.getConsistency() / 3;
        double rand = Math.random() * inverseCon;
        double aggBonus = Math.random() * (theDriver.getAggression() / 10);
        double noise = Math.random() * 125 + 75;

        speedcalc = ability - rand + aggBonus + noise;
       // printSpeedCalc(theDriver, theDriver.getQualSpeed(), ability, rand, aggBonus, noise, speedcalc);
        return speedcalc + theDriver.getSpeed() + speedConstant;

    }

    public static void printSpeedCalc(Driver theDriver, int qualSpeed, double roadOval, double rand, double aggBonus, double randomBonus, double speedCalc) {
        System.out.printf("              %-20s   %-8s   %-8s   %-8s   %-8s   %-8s\n", "Driver Name", "Ability", "Cons", "Aggr", "Random", "SpeedCalc");
        System.out.printf("speedCalc for %-20s:  %-8.1f - %-8.1f + %-8.1f + %-8.1f = %-8.1f\n", theDriver.toString(), roadOval, rand, aggBonus, randomBonus, speedCalc);
    }

    public static void waitForUser() {
        System.out.println("\nPress Enter to Continue");
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //returns true if there is a crash on the lap, false if else
    public static boolean crash(int j, int likelihood, boolean crashOthers) {
        double dnfTendency = ((double) activeDriverList.get(j).getDNFtendency() / 3) + 150;
        int newLikelihood = likelihood;
        if (crashOthers==true){
            //System.out.println("New Likelihood set to max");
            newLikelihood/=4;
        }
        double rand[] = new double[newLikelihood];
        boolean wrecked = true;
        for (int i = 0; i < rand.length && wrecked == true; i++) {
            rand[i] = Math.random() * 300;
            if (dnfTendency > rand[i]) {//this means there is a wreck
                //do nothing
            } else {
                wrecked = false;
            }
        }
        if (wrecked == true) {
            System.out.println(activeDriverList.get(j).toString() + " HAS CRASHED");
            if (crashOthers==true){
                System.out.println(activeDriverList.get(j).toString() + " was wrecked by the guy below");
            }
            activeDriverList.get(j).setDnf(true);
            crashOthers(j, likelihood, crashOthers);
            activeDriverList.get(j).setSpeed(-1);
            activeDriverList.get(j).addDnf();
        }
        return wrecked;
    }

    public static void crashOthers(int i, int likelihood, boolean crashOthers){
        int j=i+1;
        if(i!=0 && crashOthers == false){//if driver i is not the leader, and you didn't already wreck the guy in front
            if(activeDriverList.get(i-1).getSpeed()<=(activeDriverList.get(i).getSpeed()+150)) {
                //and is within 100 speed of guy in front
                j = i - 1;
                //try to wreck the guy in front! Wahoo
            }
        }

        while(j<activeDriverList.size()&&j<=(i+3)){
            Driver driver1 = activeDriverList.get(i);
            Driver driver2 = activeDriverList.get(j);
            //while driver j (the maybe wreck driver) and only check 3 closest drivers
            if(activeDriverList.get(j).isDnf()==false) {//don't crash more than once
                //System.out.println(driver1 + " speed <=  " + driver2 + " + 200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() +200) + " = " + (driver1.getSpeed()<=(driver2.getSpeed()+200)));
                //System.out.println(driver1 + " speed >= " + driver2 + " -200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() -200) + " = " + (driver1.getSpeed()>=(driver2.getSpeed()-200)));
                if((    driver1.getSpeed()<=(driver2.getSpeed()+200)&&
                        driver1.getSpeed()>=(driver2.getSpeed()-200))||
                        driver2.getSpeed()>=driver1.getSpeed()) {
                    //System.out.println("There is a driver within 200");
                    //if driver speed is within 200 of wrecking guy see if they wreck! :)
                    boolean wreckedPeople = crash(j, likelihood, true);
                    if (wreckedPeople){
                        System.out.println(activeDriverList.get(i));
                    }
                }
            }
            j++;
        }
    }

    public static int yellowLap(Race theRace, int lap, int cautionLaps){
        System.out.println("\nCAUTION Lap " + cautionLaps + " of 3");
        System.out.println("=======================");
        int speed = activeDriverList.get(0).getSpeed()+2000;
        int i = 0;

        while(i<activeDriverList.size()&&activeDriverList.get(i).isDnf()==false){
            speed-=50;
            activeDriverList.get(i).setSpeed(speed);
            i++;
        }

        return ++cautionLaps;

    }

    public static void race(Race theRace, int mode) {
        waitForUser();
        int j = 0;
        //set race ability and set starting positions (50 "speed" from one another")
        for (int i = activeDriverList.size() - 1; i >= 0; i--) {
            activeDriverList.get(i).setRaceAbility(initRaceAbility(theRace, activeDriverList.get(i)));
            activeDriverList.get(i).setSpeed(j);
            j += 50;
        }

        int cautionLaps = 0;

        //go through all the laps
        for (int lap = 1; lap <= theRace.getLaps(); lap++) {

            if (cautionLaps==0) {
                cautionLaps = greenLap(theRace, lap);
            }
            else {
                cautionLaps = yellowLap(theRace, lap, cautionLaps);
            }
            if(cautionLaps==4){
                cautionLaps = 0;
            }
            endOfLap(theRace, lap);
        }
        //conclude race stats
        System.out.println(raceWinner(activeDriverList.get(0), theRace));
        activeDriverList.get(0).addWin();
        for (int i = 0; i < 3; i++) {
            activeDriverList.get(i).addPodium();
        }
        indycar2018points(theRace);
        sortStandings();
        if (mode == 1 && activeRaceList.get(activeRaceList.size() - 1).toString().equals(theRace.toString())) {
            System.out.println(activeDriverList.get(0).toString() + " has won the Championship!");
            activeDriverList.get(0).addChampionship();
        }
    }

    //returns number of caution laps
    public static int greenLap(Race theRace, int lap){
        int cautionLaps = 0;
        for (int i = 0; i < activeDriverList.size(); i++) {
            if (activeDriverList.get(i).isDnf()==false) {
                double dspeed = createSpeed(activeDriverList.get(i), theRace);
                activeDriverList.get(i).setSpeed((int) dspeed);

            }
        }
        for (int i = 0; i < activeDriverList.size(); i++) {
            boolean isCaution = crash(i, 12, false);
            if(isCaution==true){
                cautionLaps = 1;
            }
        }
        return cautionLaps;
    }

    public static void endOfLap(Race theRace, int lap){
        Collections.sort(activeDriverList);
        activeDriverList.get(0).incrementLapsLed();
        printRaceOrder(theRace, lap);
    }

    public static void printRaceOrder(Race theRace, int lap){
        System.out.println("\n" + theRace);
        System.out.println("Lap " + lap + " of " + theRace.getLaps());
        System.out.println("____________________________________");
        for (int i = 0; i < activeDriverList.size(); i++) {
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %-5d %d\n", start + activeDriverList.get(i).getFirstName() + " " + activeDriverList.get(i).getLastName(), activeDriverList.get(i).getLapsLed(), activeDriverList.get(i).getSpeed());
        }
    }

    public static int initRaceAbility(Race theRace, Driver theDriver){
        int ability = theDriver.getSpeed() / 4;
        double newConsistency = (double) theDriver.getConsistency() / 2 + 25;
        double inverseCon = 100 - newConsistency;
        int subtract = (int) (Math.random() * inverseCon);
        if (theRace.getType().equals("Oval")) {
            ability = theDriver.getOval();
        } else if (theRace.getType().equals("RC") || theRace.getType().equals("Street")) {
            ability = theDriver.getRoad();
        }
        int rand = (int) (Math.random() * 20);
        return(ability + rand - subtract);
    }

    public static String raceWinner(Driver theDriver, Race theRace) {

        return theDriver.toString() + " has won the " + theRace.toString() + "!";
    }

    public static void existingDrivers() {
        allDriverList.add(new Driver("Alexander", "Rossi", "USA", 25));
        allDriverList.get(0).setAttributes(75, 72, 75, 73, 44, 90, 85);
        activeDriverList.add(allDriverList.get(0));
        allDriverList.add(new Driver("Carlos", "Munoz", "Columbia", 25));
        allDriverList.get(1).setAttributes(55, 53, 68, 46, 55, 75, 50);
        activeDriverList.add(allDriverList.get(1));
        allDriverList.add(new Driver("Charlie", "Kimball", "USA", 25));
        allDriverList.get(2).setAttributes(85, 50, 55, 35, 90, 55, 70);
        activeDriverList.add(allDriverList.get(2));
        allDriverList.add(new Driver("Conor", "Daly", "USA", 25));
        allDriverList.get(3).setAttributes(60, 57, 62, 53, 60, 80, 70);
        activeDriverList.add(allDriverList.get(3));
        allDriverList.add(new Driver("Ed", "Carpenter", "USA", 25));
        allDriverList.get(4).setAttributes(80, 25, 67, 43, 50, 45, 65);
        activeDriverList.add(allDriverList.get(4));
        allDriverList.add(new Driver("Ed", "Jones", "UAE", 25));
        allDriverList.get(5).setAttributes(85, 70, 50, 55, 65, 80, 70);
        activeDriverList.add(allDriverList.get(5));
        allDriverList.add(new Driver("Esteban", "Gutierrez", "Mexico", 25));
        allDriverList.get(6).setAttributes(75, 63, 49, 45, 60, 80, 70);
        activeDriverList.add(allDriverList.get(6));
        allDriverList.add(new Driver("Gabby", "Chaves", "Columbia", 25));
        allDriverList.get(7).setAttributes(60, 35, 45, 30, 45, 75, 60);
        activeDriverList.add(allDriverList.get(7));
        allDriverList.add(new Driver("Graham", "Rahal", "USA", 25));
        allDriverList.get(8).setAttributes(85, 77, 70, 80, 59, 85, 70);
        activeDriverList.add(allDriverList.get(8));
        allDriverList.add(new Driver("Helio", "Castroneves", "Brazil", 25));
        allDriverList.get(9).setAttributes(90, 72, 82, 66, 74, 80, 70);
        activeDriverList.add(allDriverList.get(9));
        allDriverList.add(new Driver("J.R.", "Hildebrand", "USA", 25));
        allDriverList.get(10).setAttributes(65, 42, 74, 58, 60, 65, 75);
        activeDriverList.add(allDriverList.get(10));
        allDriverList.add(new Driver("Jack", "Harvey", "England", 25));
        allDriverList.get(11).setAttributes(65, 58, 40, 40, 65, 75, 50);
        activeDriverList.add(allDriverList.get(11));
        allDriverList.add(new Driver("James", "Davison", "Australia", 25));
        //driverList.get(12).setAttributes();
        allDriverList.add(new Driver("James", "Hinchcliffe", "Canada", 25));
        allDriverList.get(13).setAttributes(90, 70, 62, 65, 60, 80, 70);
        activeDriverList.add(allDriverList.get(13));
        allDriverList.add(new Driver("Jay", "Howard", "England", 25));
        //driverList.get(14).setAttributes();
        allDriverList.add(new Driver("Josef", "Newgarden", "USA", 25));
        allDriverList.get(15).setAttributes(85, 85, 78, 78, 69, 95, 80);
        activeDriverList.add(allDriverList.get(15));
        allDriverList.add(new Driver("Juan Pablo", "Montoya", "Columbia", 25));
        allDriverList.get(16).setAttributes(70, 65, 70, 50, 88, 75, 55);
        allDriverList.add(new Driver("Marco", "Andretti", "USA", 25));
        allDriverList.get(17).setAttributes(85, 56, 71, 38, 38, 65, 45);
        activeDriverList.add(allDriverList.get(17));
        allDriverList.add(new Driver("Max", "Chilton", "England", 25));
        allDriverList.get(18).setAttributes(70, 52, 45, 40, 70, 75, 65);
        activeDriverList.add(allDriverList.get(18));
        allDriverList.add(new Driver("Mikhail", "Aleshin", "Russia", 25));
        allDriverList.get(19).setAttributes(80, 58, 64, 25, 91, 80, 50);
        allDriverList.add(new Driver("Oriol", "Servia", "Spain", 25));
        //driverList.get(20).setAttributes();
        allDriverList.add(new Driver("Pippa", "Mann", "USA", 25));
        allDriverList.get(21).setAttributes(80, 25, 45, 45, 35, 45, 70);
        activeDriverList.add(allDriverList.get(21));
        allDriverList.add(new Driver("Robert", "Wickens", "Canada", 25));
        allDriverList.get(22).setAttributes(70, 78, 60, 60, 55, 90, 85);
        activeDriverList.add(allDriverList.get(22));
        allDriverList.add(new Driver("Ryan", "Hunter-Reay", "USA", 25));
        allDriverList.get(23).setAttributes(80, 67, 80, 65, 50, 80, 75);
        activeDriverList.add(allDriverList.get(23));
        allDriverList.add(new Driver("Sage", "Karam", "USA", 25));
        allDriverList.get(24).setAttributes(55, 45, 70, 20, 86, 85, 40);
        allDriverList.add(new Driver("Scott", "Dixon", "New Zealand", 25));
        allDriverList.get(25).setAttributes(65, 81, 69, 95, 40, 90, 90);
        activeDriverList.add(allDriverList.get(25));
        allDriverList.add(new Driver("Sebastian", "Bourdais", "France", 25));
        allDriverList.get(26).setAttributes(70, 90, 60, 84, 66, 85, 85);
        activeDriverList.add(allDriverList.get(26));
        allDriverList.add(new Driver("Sebastian", "Saavedra", "Columbia", 25));
        allDriverList.get(27).setAttributes(80, 46, 59, 30, 73, 75, 40);
        allDriverList.add(new Driver("Simon", "Pagenaud", "France", 25));
        allDriverList.get(28).setAttributes(65, 82, 69, 92, 40, 90, 70);
        activeDriverList.add(allDriverList.get(28));
        allDriverList.add(new Driver("Spencer", "Pigot", "USA", 25));
        allDriverList.get(29).setAttributes(65, 66, 40, 55, 55, 80, 75);
        activeDriverList.add(allDriverList.get(29));
        allDriverList.add(new Driver("Takuma", "Sato", "Japan", 25));
        allDriverList.get(30).setAttributes(80, 81, 76, 50, 92, 75, 60);
        activeDriverList.add(allDriverList.get(30));
        allDriverList.add(new Driver("Tony", "Kanaan", "Brazil", 25));
        allDriverList.get(31).setAttributes(85, 55, 76, 48, 74, 60, 75);
        activeDriverList.add(allDriverList.get(31));
        allDriverList.add(new Driver("Tristian", "Vautier", "France", 25));
        //driverList.get(32).setAttributes();
        allDriverList.add(new Driver("Will", "Power", "Australia", 25));
        allDriverList.get(33).setAttributes(75, 86, 75, 70, 80, 90, 65);
        activeDriverList.add(allDriverList.get(33));
        allDriverList.add(new Driver("Zach", "Veach", "USA", 25));
        allDriverList.get(34).setAttributes(80, 65, 55, 55, 65, 85, 65);
        activeDriverList.add(allDriverList.get(34));
        allDriverList.add(new Driver("Zachary", "Claman De Melo", "Canada", 1998));
        allDriverList.get(35).setAttributes(50, 55, 45, 60, 45, 75, 65);
        activeDriverList.add(allDriverList.get(35));
        allDriverList.add(new Driver("Felix", "Rosenqvist", "Sweden", 26));
        //driverList.get(36).setAttributes();
    }

    public static void existingTracks() {
        allTrackList.add(new Track("Streets of St. Petersburg", "Street", 1.8, 14,
                "United States", "Florida", "St. Petersburg"));
        allTrackList.add(new Track("Phoenix Internatinal Raceway", "Oval", 1.022, 4,
                "United States", "Arizona", "Phoenix"));
        allTrackList.add(new Track("Streets of Long Beach", "Street", 1.968, 11,
                "United States", "California", "Long Beach"));
        allTrackList.add(new Track("Barber Motorsports Park", "RC", 2.38, 15,
                "United States", "Alabama", "Leeds"));
        allTrackList.add(new Track("Indianapolis Motor Speedway", "RC", 2.439, 13,
                "United States", "Indiana", "Speedway"));
        allTrackList.add(new Track("Indianapolis Motor Speedway", "Oval", 2.5, 4,
                "United States", "Indiana", "Speedway"));
        allTrackList.add(new Track("The Raceway at Belle Isle Park", "Street", 2.35, 14,
                "United States", "Michigan", "Detroit"));
        allTrackList.add(new Track("Texas Motor Speedway", "Oval", 1.44, 4,
                "United States", "Texas", "Fort Worth"));
        allTrackList.add(new Track("Road America", "RC", 4.048, 14,
                "United States", "Wisconson", "Elkhart Lake"));
        allTrackList.add(new Track("Iowa Speedway", "Oval", .875, 4,
                "United States", "Iowa", "Newton"));
        allTrackList.add(new Track("Streets of Toronto", "Street", 1.755, 11,
                "Canada", "Ontario", "Toronto"));
        allTrackList.add(new Track("Mid-Ohio Sports Car Course", "RC", 2.258, 13,
                "United States", "Ohio", "Lexington"));
        allTrackList.add(new Track("Pocono Raceway", "Oval", 2.5, 3,
                "United States", "Pennsylvania", "Long Pond"));
        allTrackList.add(new Track("Gateway Motorsports Park", "Oval", 1.25, 4,
                "United States", "Illinois", "Madison"));
        allTrackList.add(new Track("Portland International Raceway", "Street", 1.967, 12,
                "United States", "Oregon", "Portland"));
        allTrackList.add(new Track("Sonoma Raceway", "RC", 2.385, 12,
                "United States", "California", "Sonoma"));


        activeRaceList.add(new Race(allTrackList.get(0), "Firestone Grand Prix of St. Petersburg", 110));
        activeRaceList.add(new Race(allTrackList.get(1), "Desert Diamond Phoenix Grand Prix", 250));
        activeRaceList.add(new Race(allTrackList.get(2), "Toyota Grand Prix of Long Beach", 85));
        activeRaceList.add(new Race(allTrackList.get(3), "Honda Indy Grand Prix of Alabama", 90));
        activeRaceList.add(new Race(allTrackList.get(4), "Indycar Grand Prix", 85));
        activeRaceList.add(new Race(allTrackList.get(5), "Indianapolis 500", 200));
        activeRaceList.get(5).setDoublePoints(true);
        activeRaceList.add(new Race(allTrackList.get(6), "Chevrolet Dual in Detroit - Dual 1", 70));
        activeRaceList.add(new Race(allTrackList.get(6), "Chevrolet Dual in Detroit - Dual 2", 70));
        activeRaceList.add(new Race(allTrackList.get(7), "DXC Technology 600", 248));
        activeRaceList.add(new Race(allTrackList.get(8), "Kohler Grand Prix", 55));
        activeRaceList.add(new Race(allTrackList.get(9), "Iowa Corn 300", 300));
        activeRaceList.add(new Race(allTrackList.get(10), "Honda Indy Toronto", 85));
        activeRaceList.add(new Race(allTrackList.get(11), "Honda Indy 200 at Mid-Ohio", 90));
        activeRaceList.add(new Race(allTrackList.get(12), "ABC Supply 500", 200));
        activeRaceList.add(new Race(allTrackList.get(13), "Bommarito Automotive Group 500", 248));
        activeRaceList.add(new Race(allTrackList.get(14), "Grand Prix of Portland", 100));
        activeRaceList.add(new Race(allTrackList.get(15), "GoPro Grand Prix of Sonoma", 85));
        activeRaceList.get(activeRaceList.size()-1).setDoublePoints(true);


    }

    public static void existingManufacturers(){
        allManufacturerList.add(new Manufacturer("Chevrolet"));
        allManufacturerList.add(new Manufacturer("Honda"));
        allManufacturerList.add(new Manufacturer("Alfa Romeo"));
        allManufacturerList.get(0).setAttributes(65,65,80);
        allManufacturerList.get(1).setAttributes(75,75,65);
        activeManufacturerList.add(allManufacturerList.get(0));
        activeManufacturerList.add(allManufacturerList.get(1));
    }

    public static void existingCarsTeams(){
        allTeamList.add(new Team("Team Penske", activeManufacturerList.get(0)));
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(15), "2", 0));
        //Newgarden 2
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(9), "3", 2));



        allTeamList.add(new Team("Chip Ganassi Racing", activeManufacturerList.get(1)));

        allTeamList.add(new Team("Andretti Autosport", activeManufacturerList.get(1)));

        allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeManufacturerList.get(1)));

        allTeamList.add(new Team("Dale Coyne Racing", activeManufacturerList.get(1)));

        allTeamList.add(new Team("Schmidt Peterson Motorsports", activeManufacturerList.get(1)));

        allTeamList.add(new Team("Ed Carpenter Racing", activeManufacturerList.get(0)));

        allTeamList.add(new Team("A.J. Foyt Enterprises", activeManufacturerList.get(0)));

        allTeamList.add(new Team("Harding Racing", activeManufacturerList.get(0)));
        //88 chaves
        allTeamList.add(new Team("Carlin", activeManufacturerList.get(0)));
        //23 Kimball, 59 Chilton
        allTeamList.add(new Team("Juncos Racing", activeManufacturerList.get(0)));
        //32 Kaiser/Bender
        allTeamList.add(new Team("Dreyer & Reinbold Racing", activeManufacturerList.get(0)));
        //24 karam



    }
}
