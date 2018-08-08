import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Application {

    static List<Driver> allDriverList = new ArrayList<>();
    static List<Track> allTrackList = new ArrayList<>();
    //static List<Driver> activeDriverList = new ArrayList<>();
    static List<Race> activeRaceList = new ArrayList<>();
    static List<Car> allCarList = new ArrayList<>();
   // static List<Car> activeCarList = new ArrayList<>();
    static List<Team> allTeamList = new ArrayList<>();
    //static List<Team> activeTeamList = new ArrayList<>();
    //static List<Sponsor> allSponsorList = new ArrayList<>();
    static List<Manufacturer> allManufacturerList = new ArrayList<>();
    static List<Manufacturer> activeManufacturerList = new ArrayList<>();
    static List<Car> possibleEntries = new ArrayList<>();
    static List<Car> entryList = new ArrayList<>();

    public static void main(String[] args) {

        setUp();
        menu();
        //qualify(activeRaceList.get(1));
    }

    public static void setUp() {
        existingManufacturers();
        existingDrivers();
        //testCars();
        indycar2018cars();
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
        for (int i = 0; i < allDriverList.size(); i++) {
            allDriverList.get(i).endOfSeasonReset();

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
                        System.out.println(allDriverList.get(0).toString() + " has won the Championship!");
                        allDriverList.get(0).addChampionship();
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
        for (int i = 0; i < allDriverList.size() && allDriverList.get(i).getPoints() > 0; i++) {
            Driver theDriver = allDriverList.get(i);
            System.out.printf("%-30s %-8d %-8d %-8d %-8d %-8d %-8d\n", theDriver, theDriver.getPoints(), theDriver.getSeasonWins(),
                    theDriver.getSeasonPoles(), theDriver.getSeasonPodiums(), theDriver.getSeasonLapsLed(), theDriver.getSeasonDnf());
        }
    }

    public static void viewCareerStats() {
        System.out.println("Career Stats");
        System.out.printf("%-30s %-8s %-8s %-8s %-8s %-8s %-8s\n", "Driver", "Champ", "Wins", "Poles", "Podiums", "Laps Led", "DNFs");
        System.out.println("_______________________________________________________________________");
        for (int i = 0; i < allDriverList.size(); i++) {
            Driver theDriver = allDriverList.get(i);
            System.out.printf("%-30s %-8d %-8d %-8d %-8d %-8d %-8d\n", theDriver, theDriver.getChampionships(), theDriver.getCareerWins(),
                    theDriver.getCareerPoles(), theDriver.getCareerPodiums(), theDriver.getCareerLapsLed(), theDriver.getCareerDnfs());
        }
    }

    public static void printStandings() {
        sortStandings();
        for (int i = 0; i < allDriverList.size() && allDriverList.get(i).getPoints() > 0; i++) {
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %d\n", start + allDriverList.get(i).getFirstName() + " " + allDriverList.get(i).getLastName(), allDriverList.get(i).getPoints());
        }
    }

    public static void sortStandings() {
        for (int i = 0; i < allDriverList.size(); i++) {
            int topPoints = allDriverList.get(i).getPoints();
            int topPointsIndex = i;
            for (int j = i + 1; j < allDriverList.size(); j++) {
                if (allDriverList.get(j).getPoints() > topPoints) {
                    topPoints = allDriverList.get(j).getPoints();
                    topPointsIndex = j;
                }
            }
            if (topPointsIndex != i) {
                Driver temp = allDriverList.get(i);
                allDriverList.set(i, allDriverList.get(topPointsIndex));
                allDriverList.set(topPointsIndex, temp);
            }
        }
    }

    public static void indycar2018points(Race theRace) {
        int mostLapsLed = 0;
        int mostLapsLedIndex = 0;
        int drconstant = 1;
        if (theRace.isDoublePoints() == true) {
            drconstant = 2;
        }
        for (int pos = 0; pos < entryList.size(); pos++) {
            int driverLapsLed = entryList.get(pos).getDriver().getLapsLed();
            if (driverLapsLed > mostLapsLed) {
                mostLapsLed = driverLapsLed;
                mostLapsLedIndex = pos;
            }
            if (driverLapsLed > 0) {
                entryList.get(pos).getDriver().addPoints(1);
            }
            switch (pos) {
                case 0:
                    entryList.get(pos).getDriver().addPoints(50 * drconstant);
                    break;
                case 1:
                    entryList.get(pos).getDriver().addPoints(40 * drconstant);
                    break;
                case 2:
                    entryList.get(pos).getDriver().addPoints(35 * drconstant);
                    break;
                case 3:
                    entryList.get(pos).getDriver().addPoints(32 * drconstant);
                    break;
                case 4:
                    entryList.get(pos).getDriver().addPoints(30 * drconstant);
                    break;
                case 5:
                    entryList.get(pos).getDriver().addPoints(28 * drconstant);
                    break;
                case 6:
                    entryList.get(pos).getDriver().addPoints(26 * drconstant);
                    break;
                case 7:
                    entryList.get(pos).getDriver().addPoints(24 * drconstant);
                    break;
                case 8:
                    entryList.get(pos).getDriver().addPoints(22 * drconstant);
                    break;
                case 9:
                    entryList.get(pos).getDriver().addPoints(20 * drconstant);
                    break;
                case 10:
                    entryList.get(pos).getDriver().addPoints(19 * drconstant);
                    break;
                case 11:
                    entryList.get(pos).getDriver().addPoints(18 * drconstant);
                    break;
                case 12:
                    entryList.get(pos).getDriver().addPoints(17 * drconstant);
                    break;
                case 13:
                    entryList.get(pos).getDriver().addPoints(16 * drconstant);
                    break;
                case 14:
                    entryList.get(pos).getDriver().addPoints(15 * drconstant);
                    break;
                case 15:
                    entryList.get(pos).getDriver().addPoints(14 * drconstant);
                    break;
                case 16:
                    entryList.get(pos).getDriver().addPoints(13 * drconstant);
                    break;
                case 17:
                    entryList.get(pos).getDriver().addPoints(12 * drconstant);
                    break;
                case 18:
                    entryList.get(pos).getDriver().addPoints(11 * drconstant);
                    break;
                case 19:
                    entryList.get(pos).getDriver().addPoints(10 * drconstant);
                    break;
                case 20:
                    entryList.get(pos).getDriver().addPoints(9 * drconstant);
                    break;
                case 21:
                    entryList.get(pos).getDriver().addPoints(8 * drconstant);
                    break;
                case 22:
                    entryList.get(pos).getDriver().addPoints(7 * drconstant);
                    break;
                case 23:
                    entryList.get(pos).getDriver().addPoints(6 * drconstant);
                    break;
                default:
                    entryList.get(pos).getDriver().addPoints(5 * drconstant);
                    break;
            }
            entryList.get(pos).endOfRaceReset();
        }
        entryList.get(mostLapsLedIndex).getDriver().addPoints(2);
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
        createEntryList(theRace);
        //use entry here and under
        for (int i = 0; i < entryList.size(); i++) {
            Car theCar = entryList.get(i);
//            int inverseCon = 225 - theCar.getDriver().getConsistency();
//            double rand = Math.random() * inverseCon;
//            double aggBonus = Math.random() * (theCar.getDriver().getAggression() / 10);
//
//            if (theRace.getType().equals("RC") || theRace.getType().equals("Street")) {
//                speedcalc = theCar.getDriver().getRoad() - rand + aggBonus;
//            } else if (theRace.getType().equals("Oval")) {
//                speedcalc = theCar.getDriver().getOval() - rand + aggBonus;
//            }
//            theCar.setSpeed((int) speedcalc + 120);
            double qualSpeed = 0;
            int attempt = 0;
            while(attempt<3){
                double newQualSpeed = initRaceAbility(theRace, theCar);
                if(newQualSpeed > qualSpeed){
                    qualSpeed = newQualSpeed;
                }
                attempt++;
            }
            int qualBonus = (int) (theCar.getDriver().getQualify()/15 *(((Math.random()))));
            theCar.setSpeed((int)qualSpeed + qualBonus);
            entryList.set(i, theCar);
        }

        Collections.sort(entryList);
        if (theRace.toString().equals("Indianapolis 500")) {
            int j = 0;
            for (int i = 9; i > 0; i--) {
                entryList.get(j).getDriver().addPoints(i);
                j++;
            }
        } else {
            entryList.get(0).getDriver().addPoints(1);
        }
        entryList.get(0).getDriver().addPole();

        for (int i = 0; i < entryList.size(); i++) {
            entryList.get(i).setQualSpeed(entryList.get(i).getSpeed());
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %d\n", start + entryList.get(i).getDriver().getFirstName() + " " + entryList.get(i).getDriver().getLastName(), entryList.get(i).getSpeed());

        }

        race(theRace, mode);

    }

    public static double createSpeed(Car theCar, Race theRace) {
        int speedConstant = 1800;
        double ability = theCar.getRaceAbility() * .75;//was without the *.75
        int inverseCon = 75 - (theCar.getDriver().getConsistency() / 5) + 15; // was 4) + 10
        double rand = Math.random() * inverseCon;
        double aggBonus = Math.random() * (theCar.getDriver().getAggression() / 10);
        double noise = Math.random() * 125 + 75;
        double speedcalc = ability - rand + aggBonus + noise;
        // printSpeedCalc(theDriver, theDriver.getQualSpeed(), ability, rand, aggBonus, noise, speedcalc);
        return speedcalc + theCar.getSpeed() + speedConstant;

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
        double dnfTendency = ((double) entryList.get(j).getDriver().getDNFtendency() / 2) + 135;// / 3 + 150
        int newLikelihood = likelihood;
        if (crashOthers == true) {
            newLikelihood /= 4;
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
            System.out.println(entryList.get(j).getDriver().toString() + " HAS CRASHED");
            if (crashOthers == true) {
                System.out.println(entryList.get(j).getDriver().toString() + " was wrecked by the guy below");
            }
            entryList.get(j).getDriver().setDnf(true);
            crashOthers(j, likelihood, crashOthers);
            entryList.get(j).setSpeed(-1);
            entryList.get(j).getDriver().addDnf();
        }
        return wrecked;
    }

    public static void crashOthers(int i, int likelihood, boolean crashOthers) {
        int j = i + 1;
        if (i != 0 && crashOthers == false) {//if driver i is not the leader, and you didn't already wreck the guy in front
            if (entryList.get(i - 1).getSpeed() <= (entryList.get(i).getSpeed() + 150)) {
                //and is within 100 speed of guy in front
                j = i - 1;
                //try to wreck the guy in front! Wahoo
            }
        }

        while (j < entryList.size() && j <= (i + 3)) {
            Car car1 = entryList.get(i);
            Car car2 = entryList.get(j);
            //while driver j (the maybe wreck driver) and only check 3 closest drivers
            if (entryList.get(j).getDriver().isDnf() == false) {//don't crash more than once
                //System.out.println(driver1 + " speed <=  " + driver2 + " + 200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() +200) + " = " + (driver1.getSpeed()<=(driver2.getSpeed()+200)));
                //System.out.println(driver1 + " speed >= " + driver2 + " -200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() -200) + " = " + (driver1.getSpeed()>=(driver2.getSpeed()-200)));
                if ((car1.getSpeed() <= (car2.getSpeed() + 200) &&
                        car1.getSpeed() >= (car2.getSpeed() - 200)) ||
                        car2.getSpeed() >= car1.getSpeed()) {
                    //System.out.println("There is a driver within 200");
                    //if driver speed is within 200 of wrecking guy see if they wreck! :)
                    boolean wreckedPeople = crash(j, likelihood, true);
                    if (wreckedPeople) {
                        System.out.println(entryList.get(i));
                    }
                }
            }
            j++;
        }
    }

    public static int yellowLap(Race theRace, int lap, int cautionLaps) {
        System.out.println("\nCAUTION Lap " + cautionLaps + " of 3");
        System.out.println("=======================");
        int speed = entryList.get(0).getSpeed() + 2000;
        int i = 0;

        while (i < entryList.size() && entryList.get(i).getDriver().isDnf() == false) {
            speed -= 50;
            entryList.get(i).setSpeed(speed);
            i++;
        }
        return ++cautionLaps;
    }

    public static void race(Race theRace, int mode) {
        waitForUser();
        int j = 0;
        //set race ability and set starting positions (50 "speed" from one another")
        for (int i = entryList.size() - 1; i >= 0; i--) {
            entryList.get(i).setRaceAbility(initRaceAbility(theRace, entryList.get(i)));
            entryList.get(i).setSpeed(j);
            j += 50;
        }
        int cautionLaps = 0;
        //go through all the laps
        for (int lap = 1; lap <= theRace.getLaps(); lap++) {

            if (cautionLaps == 0) {
                cautionLaps = greenLap(theRace, lap);
            } else {
                cautionLaps = yellowLap(theRace, lap, cautionLaps);
            }
            if (cautionLaps == 4) {
                cautionLaps = 0;
            }
            endOfLap(theRace, lap);
        }
        //conclude race stats
        System.out.println(raceWinner(entryList.get(0), theRace));
        entryList.get(0).getDriver().addWin();
        for (int i = 0; i < 3; i++) {
            entryList.get(i).getDriver().addPodium();
        }
        indycar2018points(theRace);
        sortStandings();
        //if (mode == 1 && allTrackList.get(allTrackList.size() - 1).toString().equals(theRace.toString())) {
        //    System.out.println(allDriverList.get(0).toString() + " has won the Championship!");
        //    allDriverList.get(0).addChampionship();
        // }
        entryList.clear();
    }

    //returns number of caution laps
    public static int greenLap(Race theRace, int lap) {
        int cautionLaps = 0;
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getDriver().isDnf() == false) {
                double dspeed = createSpeed(entryList.get(i), theRace);
                entryList.get(i).setSpeed((int) dspeed);
            }
        }
        for (int i = 0; i < entryList.size(); i++) {
            boolean isCaution = crash(i, 12, false);
            if (isCaution == true) {
                cautionLaps = 1;
            }
        }
        return cautionLaps;
    }

    public static void endOfLap(Race theRace, int lap) {
        Collections.sort(entryList);
        entryList.get(0).getDriver().incrementLapsLed();
        printRaceOrder(theRace, lap);
    }

    public static void printRaceOrder(Race theRace, int lap) {
        System.out.println("\n" + theRace);
        System.out.println("Lap " + lap + " of " + theRace.getLaps());
        System.out.println("____________________________________");
        for (int i = 0; i < entryList.size(); i++) {
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %-5d %d\n", start + entryList.get(i).getDriver().getFirstName() + " " +
                    entryList.get(i).getDriver().getLastName(), entryList.get(i).getDriver().getLapsLed(), entryList.get(i).getSpeed());
        }
    }

    public static int initRaceAbility(Race theRace, Car theCar) {
        int ability = theCar.getSpeed() / 4;
        double newConsistency = (double) theCar.getDriver().getConsistency() / 2 + 25;
        double inverseCon = 100 - newConsistency;
        int subtract = (int) (Math.random() * inverseCon);
        if (theRace.getType().equals("Oval")) {
            ability = theCar.getDriver().getOval() + theCar.getOval();
        } else if (theRace.getType().equals("RC")) {
            ability = theCar.getDriver().getRoad() + theCar.getRoad();
        } else if (theRace.getType().equals("Street")) {
            ability = (theCar.getDriver().getStreet() + theCar.getRoad());
        }
        ability /= 2;//because we're now adding driver and car ability..  so halve it
        int rand = (int) (Math.random() * 20);
        return (ability + rand - subtract);
    }

    public static String raceWinner(Car theCar, Race theRace) {

        return theCar.getDriver().toString() + " has won the " + theRace.toString() + "!";
    }

    public static void existingDrivers() {
        allDriverList.add(new Driver("Alexander", "Rossi", "USA", 25));
        allDriverList.get(0).setAttributes(70, 76, 81, 86, 74, 74, 77, 90, 82);
        allDriverList.add(new Driver("Carlos", "Munoz", "Columbia", 25));
        allDriverList.get(1).setAttributes(50, 64, 48, 53, 70, 49, 55, 78, 58);
        allDriverList.add(new Driver("Charlie", "Kimball", "USA", 25));
        allDriverList.get(2).setAttributes(80, 59, 56, 50, 53, 41, 90, 55, 64);
        allDriverList.add(new Driver("Conor", "Daly", "USA", 25));
        allDriverList.get(3).setAttributes(60, 60, 54, 57, 62, 48, 60, 75, 69);
        allDriverList.add(new Driver("Ed", "Carpenter", "USA", 25));
        allDriverList.get(4).setAttributes(82, 77, 35, 35, 67, 43, 70, 45, 65);
        allDriverList.add(new Driver("Ed", "Jones", "UAE", 25));
        allDriverList.get(5).setAttributes(83, 70, 70, 70, 50, 69, 62, 74, 67);
        allDriverList.add(new Driver("Esteban", "Gutierrez", "Mexico", 25));
        allDriverList.get(6).setAttributes(74, 60, 63, 63, 49, 45, 60, 80, 64);
        allDriverList.add(new Driver("Gabby", "Chaves", "Columbia", 25));
        allDriverList.get(7).setAttributes(60, 45, 35, 58, 68, 45, 45, 73, 60);
        allDriverList.add(new Driver("Graham", "Rahal", "USA", 25));
        allDriverList.get(8).setAttributes(87, 65, 80, 78, 70, 78, 59, 85, 70);
        allDriverList.add(new Driver("Helio", "Castroneves", "Brazil", 25));
        allDriverList.get(9).setAttributes(85, 85, 72, 68, 82, 66, 78, 80, 70);
        allDriverList.add(new Driver("J.R.", "Hildebrand", "USA", 25));
        allDriverList.get(10).setAttributes(65, 67, 40, 42, 70, 48, 60, 65, 68);
        allDriverList.add(new Driver("Jack", "Harvey", "England", 25));
        allDriverList.get(11).setAttributes(60, 45, 60, 58, 45, 48, 50, 60, 65);
        allDriverList.add(new Driver("James", "Davison", "Australia", 25));
        allDriverList.get(12).setAttributes(55, 45, 45, 54, 48, 36, 69, 55, 65);
        allDriverList.add(new Driver("James", "Hinchcliffe", "Canada", 25));
        allDriverList.get(13).setAttributes(85, 66, 75, 70, 71, 68, 60, 82, 72);
        allDriverList.add(new Driver("Jay", "Howard", "England", 25));
        allDriverList.get(14).setAttributes(45, 45, 51, 48, 50, 35, 55, 50, 45);
        allDriverList.add(new Driver("Josef", "Newgarden", "USA", 25));
        allDriverList.get(15).setAttributes(77, 70, 79, 86, 81, 75, 76, 95, 80);
        allDriverList.add(new Driver("Juan Pablo", "Montoya", "Columbia", 25));
        allDriverList.get(16).setAttributes(71, 80, 73, 71, 73, 50, 88, 75, 55);
        allDriverList.add(new Driver("Marco", "Andretti", "USA", 25));
        allDriverList.get(17).setAttributes(88, 71, 65, 56, 70, 69, 38, 72, 48);
        allDriverList.add(new Driver("Max", "Chilton", "England", 25));
        allDriverList.get(18).setAttributes(76, 65, 68, 62, 55, 47, 70, 68, 65);
        allDriverList.add(new Driver("Mikhail", "Aleshin", "Russia", 25));
        allDriverList.get(19).setAttributes(76, 78, 67, 63, 72, 25, 91, 79, 47);
        allDriverList.add(new Driver("Oriol", "Servia", "Spain", 25));
        allDriverList.get(20).setAttributes(44, 60, 52, 47, 63, 45, 40, 40, 70);
        allDriverList.add(new Driver("Pippa", "Mann", "USA", 25));
        allDriverList.get(21).setAttributes(72, 35, 30, 30, 45, 55, 35, 45, 70);
        allDriverList.add(new Driver("Robert", "Wickens", "Canada", 25));
        allDriverList.get(22).setAttributes(65, 80, 79, 79, 71, 73, 60, 88, 85);
        allDriverList.add(new Driver("Ryan", "Hunter-Reay", "USA", 25));
        allDriverList.get(23).setAttributes(78, 70, 75, 73, 81, 72, 64, 85, 78);
        allDriverList.add(new Driver("Sage", "Karam", "USA", 25));
        allDriverList.get(24).setAttributes(55, 70, 45, 45, 69, 20, 86, 83, 30);
        allDriverList.add(new Driver("Scott", "Dixon", "New Zealand", 25));
        allDriverList.get(25).setAttributes(65, 70, 77, 87, 68, 95, 40, 85, 90);
        allDriverList.add(new Driver("Sebastian", "Bourdais", "France", 25));
        allDriverList.get(26).setAttributes(67, 69, 85, 84, 60, 70, 75, 78, 80);
        allDriverList.add(new Driver("Sebastian", "Saavedra", "Columbia", 25));
        allDriverList.get(27).setAttributes(76, 75, 46, 56, 53, 30, 73, 76, 38);
        allDriverList.add(new Driver("Simon", "Pagenaud", "France", 25));
        allDriverList.get(28).setAttributes(67, 70, 74, 73, 69, 83, 40, 79, 72);
        allDriverList.add(new Driver("Spencer", "Pigot", "USA", 25));
        allDriverList.get(29).setAttributes(65, 69, 71, 67, 40, 59, 55, 78, 70);
        allDriverList.add(new Driver("Takuma", "Sato", "Japan", 25));
        allDriverList.get(30).setAttributes(83, 70, 77, 81, 76, 45, 92, 75, 65);
        allDriverList.add(new Driver("Tony", "Kanaan", "Brazil", 25));
        allDriverList.get(31).setAttributes(84, 65, 61, 55, 77, 46, 80, 60, 65);
        allDriverList.add(new Driver("Tristian", "Vautier", "France", 25));
        allDriverList.get(32).setAttributes(63, 62, 51, 66, 48, 50, 73, 64, 48);
        allDriverList.add(new Driver("Will", "Power", "Australia", 25));
        allDriverList.get(33).setAttributes(65, 90, 84, 86, 80, 70, 82, 82, 71);
        allDriverList.add(new Driver("Zach", "Veach", "USA", 25));
        allDriverList.get(34).setAttributes(81, 55, 62, 65, 53, 63, 45, 82, 80);
        allDriverList.add(new Driver("Zachary", "Claman De Melo", "Canada", 1998));
        allDriverList.get(35).setAttributes(55, 65, 54, 63, 52, 46, 70, 75, 60);
        allDriverList.add(new Driver("Felix", "Rosenqvist", "Sweden", 26));
        allDriverList.get(36).setAttributes(55, 65, 74, 74, 47, 58, 68, 86, 75);
        allDriverList.add(new Driver("Danica", "Patrick", "USA", 1982));
        allDriverList.get(37).setAttributes(95, 68, 55, 61, 66, 45, 65, 60, 60);
        allDriverList.add(new Driver("Kyle", "Kaiser", "USA", 1996));
        allDriverList.get(38).setAttributes(60, 55, 63, 58, 60, 65, 45, 75, 65);
        allDriverList.add(new Driver("Santiago", "Urrutia", "Uruguay", 1996));
        allDriverList.get(39).setAttributes(64, 45, 50, 52, 48, 60, 60, 75, 55);
        allDriverList.add(new Driver("Pietro", "Fittipaldi", "Brazil", 1996));
        allDriverList.get(40).setAttributes(68, 65, 58, 68, 48, 48, 50, 80, 50);
        allDriverList.add(new Driver("Mattheus", "Leist", "Brazil", 1998));
        allDriverList.get(41).setAttributes(65, 60, 48, 52, 58, 45, 50, 80, 60);
        allDriverList.add(new Driver("Stefano", "Coletti", "Monaco", 1989));
        allDriverList.get(42).setAttributes(45, 55, 50, 48, 30, 45, 55, 68, 67);
        allDriverList.add(new Driver("Buddy", "Lazier", "USA", 1967));
        allDriverList.get(43).setAttributes(75, 44, 36, 40, 50, 40, 35, 45, 45);
        allDriverList.add(new Driver("Stefan", "Wilson", "USA", 1989));
        allDriverList.get(44).setAttributes(55, 45, 52, 52, 52, 40, 35, 50, 43);
        allDriverList.add(new Driver("Rene", "Binnder", "Austria", 1992));
        allDriverList.get(45).setAttributes(55, 60, 55, 65, 45, 33, 55, 63, 65);
        allDriverList.add(new Driver("Jordan", "King", "England", 1994));
        allDriverList.get(46).setAttributes(55, 45, 57, 63, 40, 48, 50, 70, 72);
        allDriverList.add(new Driver("Patricio", "O'Ward", "Mexico", 1999));
        allDriverList.get(47).setAttributes(60, 60, 44, 50, 25, 25, 55, 70, 65);
        allDriverList.add(new Driver("Colton", "Herta", "USA", 2000));
        allDriverList.get(48).setAttributes(75, 65, 42, 45, 40, 15, 59, 85, 70);
    }

    public static void testCars(){
        allDriverList.add(new Driver("Bad", "Driver","USA",1990));
        allDriverList.get(allDriverList.size()-1).setAttributes(50,50,50,50,50,50,50,50,50);
        allDriverList.add(new Driver("Average", "Driver","USA",1990));
        allDriverList.get(allDriverList.size()-1).setAttributes(65,65,65,65,65,65,50,65,65);
        allDriverList.add(new Driver("Great", "Driver","USA",1990));
        allDriverList.get(allDriverList.size()-1).setAttributes(80,80,80,80,80,80,50,80,80);
        allTeamList.add(new Team("Bad Team",allManufacturerList.get(0)));
        allTeamList.get(allTeamList.size()-1).setAttributes(50,50,50);
        allCarList.add(allTeamList.get(allTeamList.size()-1).createCar(allDriverList.get(51),"99", 0));
        allTeamList.add(new Team("Average Team",allManufacturerList.get(0)));
        allTeamList.get(allTeamList.size()-1).setAttributes(65,65,65);
        allCarList.add(allTeamList.get(allTeamList.size()-1).createCar(allDriverList.get(50),"98", 0));
        allTeamList.add(new Team("Bad Team",allManufacturerList.get(0)));
        allTeamList.get(allTeamList.size()-1).setAttributes(80,80,80);
        allCarList.add(allTeamList.get(allTeamList.size()-1).createCar(allDriverList.get(49),"97", 0));
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
        allTrackList.add(new Track("Laguna Seca Raceway", "RC", 2.238, 11,
                "United States", "California", "Monterey"));


        setSchedule(2018);


    }

    public static void setSchedule(int year) {
        if (year == 2018) {
            activeRaceList.add(new Race(allTrackList.get(0), "Firestone Grand Prix of St. Petersburg", 110, 4));
            activeRaceList.add(new Race(allTrackList.get(1), "Desert Diamond Phoenix Grand Prix", 250, 2));
            activeRaceList.add(new Race(allTrackList.get(2), "Toyota Grand Prix of Long Beach", 85, 5));
            activeRaceList.add(new Race(allTrackList.get(3), "Honda Indy Grand Prix of Alabama", 90, 2));
            activeRaceList.add(new Race(allTrackList.get(4), "Indycar Grand Prix", 85, 4));
            activeRaceList.add(new Race(allTrackList.get(5), "Indianapolis 500", 200, 10));
            activeRaceList.get(5).setDoublePoints(true);
            activeRaceList.add(new Race(allTrackList.get(6), "Chevrolet Dual in Detroit - Dual 1", 70, 3));
            activeRaceList.add(new Race(allTrackList.get(6), "Chevrolet Dual in Detroit - Dual 2", 70, 3));
            activeRaceList.add(new Race(allTrackList.get(7), "DXC Technology 600", 248, 4));
            activeRaceList.add(new Race(allTrackList.get(8), "Kohler Grand Prix", 55, 4));
            activeRaceList.add(new Race(allTrackList.get(9), "Iowa Corn 300", 300, 2));
            activeRaceList.add(new Race(allTrackList.get(10), "Honda Indy Toronto", 85, 3));
            activeRaceList.add(new Race(allTrackList.get(11), "Honda Indy 200 at Mid-Ohio", 90, 2));
            activeRaceList.add(new Race(allTrackList.get(12), "ABC Supply 500", 200, 2));
            activeRaceList.add(new Race(allTrackList.get(13), "Bommarito Automotive Group 500", 248, 2));
            activeRaceList.add(new Race(allTrackList.get(14), "Grand Prix of Portland", 100, 1));
            activeRaceList.add(new Race(allTrackList.get(15), "GoPro Grand Prix of Sonoma", 85, 4));
            activeRaceList.get(activeRaceList.size() - 1).setDoublePoints(true);
        }
        if (year == 2019) {

        }
    }

    public static void existingManufacturers() {
        allManufacturerList.add(new Manufacturer("Chevrolet"));
        allManufacturerList.add(new Manufacturer("Honda"));
        allManufacturerList.add(new Manufacturer("Alfa-Romeo"));
        allManufacturerList.get(0).setAttributes(65, 65, 80);
        allManufacturerList.get(1).setAttributes(75, 75, 65);
        activeManufacturerList.add(allManufacturerList.get(0));
        activeManufacturerList.add(allManufacturerList.get(1));
    }

    public static void createEntryList(Race theRace) {
        for (int i = 0; i < allCarList.size(); i++) {
            Car theCar = allCarList.get(i);

            if (theCar.getFullTime() == 0) {//full
                entryList.add(theCar);
            } else if (theCar.getFullTime() == 1) {//half
                if (theRace.getPrestige() > 4) {
                    entryList.add(theCar);//currently adds part time cars to prestigious races, later on maybe keep track
                    //of how many races done and then randomize it a bit
                }
            } else if (theCar.getFullTime() == 2) {//road/street
                if (theRace.getType() != "Oval") {
                    entryList.add(theCar);
                }
            } else if (theCar.getFullTime() == 3) {//ovals
                if (theRace.getType() == "Oval") {
                    entryList.add(theCar);
                }
            } else if (theCar.getFullTime() == 4) {//May
                if (theRace.getCity() == "Speedway") {
                    entryList.add(theCar);
                }
            } else if (theCar.getFullTime() == 5) {
                if (theRace.getRaceTitle() == "Indianapolis 500") {
                    entryList.add(theCar);
                }
            }
        }

    }

    public static void indycar2018cars() {
        allTeamList.add(new Team("Team Penske", activeManufacturerList.get(0)));
        allTeamList.get(0).setAttributes(78, 78, 82);
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(15), "2", 0));
        //Newgarden 2
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(9), "3", 4));
        //Castroneves 3
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(33), "12", 0));
        //Power 12
        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(28), "22", 0));
        //Pagenaud 22

        allTeamList.add(new Team("Chip Ganassi Racing", activeManufacturerList.get(1)));
        allTeamList.get(1).setAttributes(77, 68, 89);
        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(25), "9", 0));
        //Dixon 9
        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(5), "10", 0));
        //Jones 10

        allTeamList.add(new Team("Andretti Autosport", activeManufacturerList.get(1)));
        allTeamList.get(2).setAttributes(75, 83, 75);
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(44), "25", 5));
        //Wilson 25
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(34), "26", 0));
        //Veach 26
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(0), "27", 0));
        //Rossi 27
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(23), "28", 0));
        //Hunter-Reay 28
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(1), "29", 0));
        //Munoz 29
        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(17), "98", 0));
        //Marco 98

        allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeManufacturerList.get(1)));
        allTeamList.get(3).setAttributes(73, 65, 68);
        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(8), "15", 0));
        //Rahal 15
        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(30), "30", 0));
        //Sato 30
        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(20), "64", 5));
        //Servia 64

        allTeamList.add(new Team("Dale Coyne Racing", activeManufacturerList.get(1)));
        allTeamList.get(4).setAttributes(70, 55, 70);
        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(26), "18", 0));
        //Bourdais 18
        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(35), "19", 0));
        //de melo/fittipaldi 19
        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(3), "17", 5));
        //Daly 17
        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(21), "63", 5));
        //Pippa 63

        allTeamList.add(new Team("Schmidt Peterson Motorsports", activeManufacturerList.get(1)));
        allTeamList.get(5).setAttributes(74, 75, 70);
        allCarList.add(allTeamList.get(5).createCar(allDriverList.get(13), "5", 0));
        //Hinch 5
        allCarList.add(allTeamList.get(5).createCar(allDriverList.get(22), "6", 0));
        //Wickens 6
        allCarList.add(allTeamList.get(5).createCar(allDriverList.get(14), "7", 5));
        //Howard 7
        allCarList.add(allTeamList.get(5).createCar(allDriverList.get(11), "60", 4));
        //Harvey 60

        allTeamList.add(new Team("Ed Carpenter Racing", activeManufacturerList.get(0)));
        allTeamList.get(6).setAttributes(68, 73, 65);
        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(4), "20", 3));
        //Carpenter 20
        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(46),"20",2));
        //King 20
        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(29), "21", 0));
        //Pigot 21
        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(37), "13", 5));
        //Danica 13

        allTeamList.add(new Team("A.J. Foyt Enterprises", activeManufacturerList.get(0)));
        allTeamList.get(0).setAttributes(55, 60, 60);
        allCarList.add(allTeamList.get(7).createCar(allDriverList.get(41), "4", 0));
        //Leist 4
        allCarList.add(allTeamList.get(7).createCar(allDriverList.get(31), "14", 0));
        //Kanaan 14
        allCarList.add(allTeamList.get(7).createCar(allDriverList.get(12), "33", 5));
        //Davison 33

        allTeamList.add(new Team("Harding Racing", activeManufacturerList.get(0)));
        allTeamList.get(8).setAttributes(50, 50, 50);
        allCarList.add(allTeamList.get(8).createCar(allDriverList.get(7), "88", 1));
        //88 chaves

        allTeamList.add(new Team("Carlin", activeManufacturerList.get(0)));
        allTeamList.get(9).setAttributes(55, 55, 55);
        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(2), "23", 0));
        //23 Kimball
        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(18), "59", 0));
        //59 Chilton

        allTeamList.add(new Team("Juncos Racing", activeManufacturerList.get(0)));
        allTeamList.get(10).setAttributes(50, 50, 50);
        allCarList.add(allTeamList.get(10).createCar(allDriverList.get(38), "32", 0));
        //32 Kaiser/Bender

        allTeamList.add(new Team("Dreyer & Reinbold Racing", activeManufacturerList.get(0)));
        allTeamList.get(11).setAttributes(40, 50, 40);
        allCarList.add(allTeamList.get(11).createCar(allDriverList.get(24), "24", 1));
        //24 karam


        for (int i = 0; i < allCarList.size(); i++) {
            possibleEntries.add(allCarList.get(i));

        }
    }
}
