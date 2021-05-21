import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class Application {

    static List<Driver> allDriverList = new ArrayList();
    static List<Track> allTrackList = new ArrayList();
    //static List<Driver> activeDriverList = new ArrayList<>();
    static List<Race> activeRaceList = new ArrayList();
    static List<Car> allCarList = new ArrayList();
    // static List<Car> activeCarList = new ArrayList<>();
    static List<Team> allTeamList = new ArrayList();
    //static List<Team> activeTeamList = new ArrayList<>();
    //static List<Sponsor> allSponsorList = new ArrayList<>();
    static List<Engine> allEngineList = new ArrayList();
    static List<Engine> activeEngineList = new ArrayList();
    static List<Car> possibleEntries = new ArrayList();
    static List<Car> entryList = new ArrayList();
    static int year = 2021;
    static int wait = 3;//1 is per lap, 2 is stop for caution, 3 is per race, 4 is per season

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        setUp();
        menu();
        //qualify(activeRaceList.get(1));
    }

    public static void setUp() throws IOException, GeneralSecurityException {
        allEngineList.clear();
        allCarList.clear();
        allTeamList.clear();
        allDriverList.clear();
        allTrackList.clear();
        activeRaceList.clear();


        /*existingManufacturers();
        //existingDrivers();
        SheetsQuickstart.getDriverList(allDriverList);
        //testCars();
        setCars(year);
        existingTracks();*/

        allEngineList = SheetsQuickstart.getEngineList();
        allTeamList = SheetsQuickstart.getTeamList();
        SheetsQuickstart.getDriverList(allDriverList);
        allTrackList = SheetsQuickstart.getTrackList();
        activeRaceList = SheetsQuickstart.setRaceListByYear(allTrackList, year);
        setCars(year);

    }

    public static void menu() throws IOException, GeneralSecurityException {
        Scanner scan = new Scanner(System.in);
        boolean go = true;
        while (go) {
            System.out.println("Main Menu");
            System.out.println("____________");
            System.out.println("1. Sim Race");
            System.out.println("2. Season Mode");
            System.out.println("3. Career Mode");
            System.out.println("4. Settings");
            System.out.println("5. Exit");
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
                    settingsMenu();
                    setUp();
                    break;
                case 5:
                    scan.close();
                    go = false;
                    break;
                default:
                    System.out.println("Try entering a different number");
                    break;
            }
        }
    }

    public static void settingsMenu() {
        Scanner scan = new Scanner(System.in);
        boolean go = true;
        while (go) {
            sortStandings();
            System.out.println("\nSettings Menu");
            System.out.println("____________________");
            System.out.println("1. Select Year");
            System.out.println("2. Create Team");
            System.out.println("3. Create Car");
            System.out.println("4. Create Driver");
            System.out.println("5. Create Entry");
            System.out.println("6. Set Sim Mode");
            System.out.println("7. Testing");
            System.out.println("8. Exit");
            int input = scan.nextInt();
            boolean yearGo = true;
            switch (input) {
                case 1:
                    while (yearGo) {
                        System.out.println("1. Indycar 2018");
                        System.out.println("2. Indycar 2019");
                        System.out.println("3. Indycar 2020");
                        System.out.println("4. Indycar 2021");
                        int newInput = scan.nextInt();
                        switch (newInput) {
                            case 1:
                                year = 2018;
                                yearGo = false;
                                break;
                            case 2:
                                year = 2019;
                                yearGo = false;
                                break;
                            case 3:
                                year = 2020;
                                yearGo = false;
                                break;
                            case 4:
                                year = 2021;
                                yearGo = false;
                            default:
                                System.out.println("Try entering a different number");
                        }
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Set Sim Mode");
                    System.out.println("______________");
                    System.out.println("1. Sim lap by lap");
                    System.out.println("2. Pause sim for Cautions");
                    System.out.println("3. Pause sim at end of Race");
                    System.out.println("4. Pause sim at end of Season");
                    int newInput = scan.nextInt();
                    if (newInput > 0 && newInput < 5) {
                        wait = newInput;
                    } else {
                        System.out.println("Try entering a different number.");
                    }
                    break;
                case 7:
                    testing();
                    break;
                case 8:
                    go = false;
                    break;
                default:
                    System.out.println("Try entering a different number");
                    break;
            }
        }
    }

    public static void testing() {
        Car car = allCarList.get(0);
        Race race = activeRaceList.get(5);
        System.out.println(race);
/*        for (int i = 0; i < 150; i++) {
            System.out.println(car.calculateLapTime(race, 1));

        }*/

        for (int i = 0; i < allCarList.size(); i++) {
            car = allCarList.get(i);
            System.out.println(car.getDriver());
            car.setRaceAbility(race);
            System.out.println(car.calculateLapTime(race, LapType.QUALIFY));
            //System.out.println(car.getRaceSpeed(race));
        }
/*        Car car = allCarList.get(0);
        for (int i = 0; i < activeRaceList.size(); i++) {
            Race race = activeRaceList.get(i);
            System.out.println(race);
            double lapTime = race.getTrack().getLapTime();
            System.out.println(car.getLapTime(race) + " seconds");
            System.out.println(car.calculateMPH(lapTime, race.getTrack()) + " MPH");
            System.out.println("____________________\n");
        }*/
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
            int input = 1;
            if (wait != 4 || raceNum >= activeRaceList.size()) {
                input = scan.nextInt();
            }
            switch (input) {
                case 1:
                    if (raceNum < activeRaceList.size()) {
                        qualify(activeRaceList.get(raceNum), 1);
                        raceNum++;
                    } else {
                        System.out.println(allDriverList.get(0).toString() + " has won the Championship!");
                        allDriverList.get(0).addChampionship();
                        viewSeasonStats();
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
            String start = (i + 1) + ". ";
            Driver theDriver = allDriverList.get(i);
            System.out.printf("%5s %-25s %-8d %-8d %-8d %-8d %-8d %-8d\n", start, theDriver, theDriver.getPoints(), theDriver.getSeasonWins(),
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
        if (theRace.isDoublePoints()) {
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
//            if (theRace.getType().equals(TrackType.ROAD_COURSE) || theRace.getType().equals(TrackType.ROAD_COURSE)) {
//                speedcalc = theCar.getDriver().getRoad() - rand + aggBonus;
//            } else if (theRace.getType().equals(TrackType.LARGE_OVAL)) {
//                speedcalc = theCar.getDriver().getSmallOval() - rand + aggBonus;
//            }
//            theCar.setSpeed((int) speedcalc + 120);
            /*double qualSpeed = 0;
            int attempt = 0;
            while (attempt < 3) {
                double newQualSpeed = initRaceAbility(theRace, theCar);//calls initRaceAbility but doesn't save it to the car.. that's done later
                if (newQualSpeed > qualSpeed) {
                    qualSpeed = newQualSpeed;
                }
                attempt++;
            }
            int qualBonus = (int) (theCar.getDriver().getQualify() / 16 * (((Math.random()))));
            theCar.setSpeed((int) qualSpeed + qualBonus);
            entryList.set(i, theCar);*/
            theCar.setRaceAbility(theRace);
            theCar.setSpeed(theCar.calculateLapTime(theRace, LapType.QUALIFY));
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

        String bumpString = "";
        while (entryList.size() > 33) {
            bumpString += (entryList.get(entryList.size() - 1).getDriver() + " was bumped.\n");
            entryList.remove(entryList.size() - 1);
        }

        for (int i = 0; i < entryList.size(); i++) {
           // entryList.get(i).setQualifyTime(entryList.get(i).getSpeed());
            entryList.get(i).setPosition(i + 1);
            String start = (i + 1) + ". ";
            System.out.printf("%-30s %f\n", start + entryList.get(i).getDriver().getFirstName() + " " + entryList.get(i).getDriver().getLastName(), entryList.get(i).getSpeed());
        }

        if (!Objects.equals(bumpString, "")) {
            System.out.println(bumpString);
        }
        //move all points related things to the points methods, call the method here. Make point method take in a 1 for qual, 2 for race or something
        race(theRace, mode);

    }

   /* public static double createSpeed(Car theCar, Race theRace) {
        int speedConstant = 1800;
        double ability = theCar.getRaceAbility() * .75;//was without the *.75
        int inverseCon = 75 - (theCar.getDriver().getConsistency() / 5) + 15; // was 4) + 10
        double rand = Math.random() * inverseCon;
        double aggBonus = Math.random() * (theCar.getDriver().getAggression() / 10);
        double noise = Math.random() * 125 + 75;
        double speedcalc = ability - rand + aggBonus + noise;
        // printSpeedCalc(theDriver, theDriver.getQualifyTime(), ability, rand, aggBonus, noise, speedcalc);
        return speedcalc + theCar.getSpeed() + speedConstant;
    }*/

    public static void printSpeedCalc(Driver theDriver, int qualSpeed, double roadOval, double rand, double aggBonus, double randomBonus, double speedCalc) {
        System.out.printf("              %-20s   %-8s   %-8s   %-8s   %-8s   %-8s\n", "Driver Name", "Ability", "Cons", "Aggr", "Random", "SpeedCalc");
        System.out.printf("speedCalc for %-20s:  %-8.1f - %-8.1f + %-8.1f + %-8.1f = %-8.1f\n", theDriver.toString(), roadOval, rand, aggBonus, randomBonus, speedCalc);
    }

    public static void waitForUser() {
        //Set scenarios here
        //1. Wait at end of every lap
        //2. Wait for caution laps
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
        if (!entryList.get(j).getDriver().isDnf()) {
            double dnfTendency = ((double) entryList.get(j).getDriver().getDNFtendency() / 2) + 135;// / 3 + 150
            int newLikelihood = likelihood;
            if (crashOthers) {
                newLikelihood /= 4;
            }
            double rand[] = new double[newLikelihood];
            boolean wrecked = true;
            for (int i = 0; i < rand.length && wrecked; i++) {
                rand[i] = Math.random() * 300;
                if (dnfTendency > rand[i]) {//this means there is a wreck
                    //do nothing
                } else {
                    wrecked = false;
                }
            }
            if (wrecked) {
                System.out.println(entryList.get(j).getDriver().toString() + " has crashed");
                if (crashOthers) {
                    System.out.print(entryList.get(j).getDriver().toString() + " was wrecked by ");
                }
                entryList.get(j).getDriver().setDnf(true);
                crashOthers(j, likelihood, crashOthers);
                entryList.get(j).setSpeed(Double.MAX_VALUE);
                entryList.get(j).getDriver().addDnf();
            }
            return wrecked;
        } else return false;
    }

    public static void crashOthers(int i, int likelihood, boolean crashOthers) {
        int j = i + 1;
        if (i != 0 && !crashOthers) {//if driver i is not the leader, and you didn't already wreck the guy in front
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
            if (!entryList.get(j).getDriver().isDnf()) {//don't crash more than once
                //System.out.println(driver1 + " speed <=  " + driver2 + " + 200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() +200) + " = " + (driver1.getSpeed()<=(driver2.getSpeed()+200)));
                //System.out.println(driver1 + " speed >= " + driver2 + " -200: " + driver1.getSpeed() + " + " + (driver2.getSpeed() -200) + " = " + (driver1.getSpeed()>=(driver2.getSpeed()-200)));
                if ((car1.getSpeed() <= (car2.getSpeed() + 200) &&
                        car1.getSpeed() >= (car2.getSpeed() - 200)) ||
                        car2.getSpeed() >= car1.getSpeed()) {
                    //System.out.println("There is a driver within 200");
                    //if driver speed is within 200 of wrecking guy see if they wreck! :)
                    boolean wreckedPeople = crash(j, likelihood, true);
                    if (wreckedPeople) {
                        System.out.println(entryList.get(i).getDriver());
                    }
                }
            }
            j++;
        }
    }

    public static int yellowLap(Race theRace, int lap, int cautionLaps) {
        System.out.println("\nCAUTION Lap " + cautionLaps + " of 3");
        System.out.println("=======================");
        double speed = entryList.get(0).getSpeed() + 2000;//no clue why I'm adding 2000 here, maybe an average lap?
        int i = 0;

        while (i < entryList.size() && !entryList.get(i).getDriver().isDnf()) {

            //fuel stuff here
            int mode = 1;//1 if caution, 2 if conserve, 3 normal, 4 push
            entryList.get(i).decreaseFuel(theRace.getMiles(), mode);
            //tire stuff here


            speed +=1;
            entryList.get(i).setSpeed(speed);
            i++;
            //if(wait == 1){
            //    waitForUser();
            //}
        }
        return ++cautionLaps;
    }

    public static void race(Race theRace, int mode) {
        if (wait < 4) {
            waitForUser();
        }
        double j = 0;
        //set race ability and set starting positions (50 "speed" from one another")
        for (int i= 0; i < entryList.size(); i++) {
            entryList.get(i).addFuel(18.5);
            entryList.get(i).changeTires(1);
            entryList.get(i).setRaceAbility(theRace);
            if (theRace.getRaceTitle().equals("Indianapolis 500")){
                // 0 1 2
                // 3 4 5
                // 6 7 8
                if (i%3==0){
                    j+=.25;
                } else {
                    j+=.05;
                }
            } else {
                // 0 1
                // 2 3
                // 4 5
                if (i%2==0){
                    j+=.25;
                } else {
                    j+= .05;
                }
            }
            entryList.get(i).setSpeed(j);
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
            if (wait == 1) {
                waitForUser();
            } else if (wait == 2 && cautionLaps == 1) {
                waitForUser();
            }
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
            if (!entryList.get(i).getDriver().isDnf()) {

                //fuel stuff here
                int mode = 3;//1 if caution, 2 if conserve, 3 normal, 4 push
                entryList.get(i).decreaseFuel(theRace.getMiles(), mode);
                //tire stuff here

                //double dspeed = createSpeed(entryList.get(i), theRace);
                entryList.get(i).doLap(theRace, LapType.GREEN);

            }
        }
        for (int i = 0; i < entryList.size(); i++) {
            boolean isCaution = crash(i, 12, false);
            if (isCaution) {
                cautionLaps = 1;
            }
        }
        return cautionLaps;
    }

    public static void endOfLap(Race theRace, int lap) {
        Collections.sort(entryList);

        entryList.get(0).getDriver().incrementLapsLed();
        if (wait != 4) {
            printRaceOrder(theRace, lap);
        }
    }

    public static void printRaceOrder(Race theRace, int lap) {
        System.out.println("\n" + theRace);
        System.out.println("Lap " + lap + " of " + theRace.getLaps());
        System.out.println("____________________________________");
        for (int i = 0; i < entryList.size(); i++) {
            String start = (i + 1) + ".";
            System.out.printf("%-5s %-5s %-25s %-5d", start, "#" + entryList.get(i).getNumber(), entryList.get(i).getDriver().getFirstName()
                    + " " + entryList.get(i).getDriver().getLastName(), entryList.get(i).getDriver().getLapsLed());
            int posChange = entryList.get(i).getPosition() - (i + 1);
            entryList.get(i).setPosition(i + 1);
            if (posChange != 0) {
                if (posChange > 0) {
                    System.out.printf("%-5s", "+" + posChange);
                } else {
                    System.out.printf("%-5s", posChange);
                }
            } else {
                System.out.print("     ");
            }
            if (entryList.get(i).getSpeed() == Double.MAX_VALUE) {
                System.out.print("OUT");
            }
            //System.out.printf("%.2f", entryList.get(i).getFuel());
            System.out.println();
        }
    }

    public static double initRaceAbility(Race theRace, Car theCar) {
        double ability = theCar.getSpeed() / 4;
        double newConsistency = (double) theCar.getDriver().getConsistency() / 2 + 25;
        double inverseCon = 100 - newConsistency;
        int subtract = (int) (Math.random() * inverseCon);
        if (theRace.getType() == TrackType.LARGE_OVAL) {
            ability = theCar.getDriver().getLargeOval() + .75 * theCar.getLargeOval();
        } else if (theRace.getType().equals(TrackType.SMALL_OVAL)) {
            ability = theCar.getDriver().getSmallOval() + .75 * theCar.getSmallOval();
        } else if (theRace.getType().equals(TrackType.ROAD_COURSE)) {
            ability = theCar.getDriver().getRoad() + .75 * theCar.getRoad();
        } else if (theRace.getType().equals(TrackType.STREET_COURSE)) {
            ability = (theCar.getDriver().getStreet() + theCar.getStreet());
        }
        ability /= 1.75;//because we're now adding driver and car ability..  so halve it
        int rand = (int) (Math.random() * 20);
        return (ability + rand - subtract);
    }

    public static String raceWinner(Car theCar, Race theRace) {
        return theCar.getDriver().toString() + " has won the " + theRace.toString() + "!";
    }

    public static void createEntryList(Race theRace) {
        for (int i = 0; i < allCarList.size(); i++) {
            Car theCar = allCarList.get(i);

            if (theCar.getEntryType() == EntryType.FULL) {//full
                entryList.add(theCar);
            } else if (theCar.getEntryType() == EntryType.HALF) {//half
                if (theRace.getPrestige() > 4) {
                    entryList.add(theCar);//currently adds part time cars to prestigious races, later on maybe keep track
                    //of how many races done and then randomize it a bit
                }
            } else if (theCar.getEntryType() == EntryType.ROAD_STREET) {//road/street
                if (!theRace.getType().isOval()) {
                    entryList.add(theCar);
                }
            } else if (theCar.getEntryType() == EntryType.OVAL) {//ovals
                if (theRace.getType().isOval()) {
                    entryList.add(theCar);
                }
            } else if (theCar.getEntryType() == EntryType.MAY) {//May
                if (theRace.getCity().equals("Speedway")) {
                    entryList.add(theCar);
                }
            } else if (theCar.getEntryType() == EntryType.INDY_500) {
                if (theRace.getRaceTitle().equals("Indianapolis 500")) {
                    entryList.add(theCar);
                }
            }
        }

    }

    public static void setCars(int year) throws IOException, GeneralSecurityException {
 /*       if (year == 2018) {
            allTeamList.add(new Team("Team Penske", activeEngineList.get(0)));
            allTeamList.get(0).setAttributes(78, 78, 82);
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(15), "2", 0));
            //Newgarden 2
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(9), "3", 4));
            //Castroneves 3
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(33), "12", 0));
            //Power 12
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(28), "22", 0));
            //Pagenaud 22

            allTeamList.add(new Team("Chip Ganassi Racing", activeEngineList.get(1)));
            allTeamList.get(1).setAttributes(77, 68, 89);
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(25), "9", 0));
            //Dixon 9
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(5), "10", 0));
            //Jones 10

            allTeamList.add(new Team("Andretti Autosport", activeEngineList.get(1)));
            allTeamList.get(2).setAttributes(75, 83, 75);
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(44), "25", 5));
            //Wilson 25
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(34), "26", 0));
            //Veach 26
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(0), "27", 0));
            //Rossi 27
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(23), "28", 0));
            //Hunter-Reay 28
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(1), "29", 5));
            //Munoz 29
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(17), "98", 0));
            //Marco 98

            allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeEngineList.get(1)));
            allTeamList.get(3).setAttributes(73, 65, 68);
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(8), "15", 0));
            //Rahal 15
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(30), "30", 0));
            //Sato 30
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(20), "64", 5));
            //Servia 64

            allTeamList.add(new Team("Dale Coyne Racing", activeEngineList.get(1)));
            allTeamList.get(4).setAttributes(70, 55, 70);
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(26), "18", 0));
            //Bourdais 18
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(35), "19", 0));
            //de melo/fittipaldi 19
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(3), "17", 5));
            //Daly 17
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(21), "63", 5));
            //Pippa 63

            allTeamList.add(new Team("Schmidt Peterson Motorsports", activeEngineList.get(1)));
            allTeamList.get(5).setAttributes(74, 75, 70);
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(13), "5", 0));
            //Hinch 5
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(22), "6", 0));
            //Wickens 6
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(14), "7", 5));
            //Howard 7
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(11), "60", 4));
            //Harvey 60

            allTeamList.add(new Team("Ed Carpenter Racing", activeEngineList.get(0)));
            allTeamList.get(6).setAttributes(68, 73, 65);
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(4), "20", 3));
            //Carpenter 20
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(46), "20", 2));
            //King 20
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(29), "21", 0));
            //Pigot 21
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(37), "13", 5));
            //Danica 13

            allTeamList.add(new Team("A.J. Foyt Enterprises", activeEngineList.get(0)));
            allTeamList.get(7).setAttributes(55, 60, 60);
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(41), "4", 0));
            //Leist 4
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(31), "14", 0));
            //Kanaan 14
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(12), "33", 5));
            //Davison 33

            allTeamList.add(new Team("Harding Racing", activeEngineList.get(0)));
            allTeamList.get(8).setAttributes(50, 50, 50);
            allCarList.add(allTeamList.get(8).createCar(allDriverList.get(7), "88", 1));
            //88 chaves

            allTeamList.add(new Team("Carlin", activeEngineList.get(0)));
            allTeamList.get(9).setAttributes(55, 55, 55);
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(2), "23", 0));
            //23 Kimball
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(18), "59", 0));
            //59 Chilton

            allTeamList.add(new Team("Juncos Racing", activeEngineList.get(0)));
            allTeamList.get(10).setAttributes(45, 45, 45);
            allCarList.add(allTeamList.get(10).createCar(allDriverList.get(38), "32", 0));
            //32 Kaiser/Bender

            allTeamList.add(new Team("Dreyer & Reinbold Racing", activeEngineList.get(0)));
            allTeamList.get(11).setAttributes(40, 50, 40);
            allCarList.add(allTeamList.get(11).createCar(allDriverList.get(24), "24", 1));
            //24 karam
        } else if (year == 2019) {
            allTeamList.add(new Team("Team Penske", activeEngineList.get(0)));
            allTeamList.get(0).setAttributes(78, 78, 82);
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(15), "2", 0));
            //Newgarden 2
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(9), "3", 4));
            //Castroneves 3
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(33), "12", 0));
            //Power 12
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(28), "22", 0));
            //Pagenaud 22


            allTeamList.add(new Team("Chip Ganassi Racing", activeEngineList.get(1)));
            allTeamList.get(1).setAttributes(75, 68, 89);
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(25), "9", 0));
            //Dixon 9
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(36), "10", 0));
            //Rosenqvist 10

            allTeamList.add(new Team("Andretti Autosport", activeEngineList.get(1)));
            allTeamList.get(2).setAttributes(75, 82, 75);
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(3), "25", 5));
            //Daly 25
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(34), "26", 0));
            //Veach 26
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(0), "27", 0));
            //Rossi 27
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(23), "28", 0));
            //Hunter-Reay 28
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(17), "98", 0));
            //Marco 98

            allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeEngineList.get(1)));
            allTeamList.get(3).setAttributes(72, 72, 68);
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(8), "15", 0));
            //Rahal 15
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(30), "30", 0));
            //Sato 30
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(46), "42", 5));
            //King 42

            allTeamList.add(new Team("Dale Coyne Racing", activeEngineList.get(1)));
            allTeamList.get(4).setAttributes(70, 55, 70);
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(26), "18", 0));
            //Bourdais 18
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(50), "19", 0));
            //Ferrucci 19
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(12), "33", 5));
            //Davison 33

            allTeamList.add(new Team("Arrow Schmidt Peterson Motorsports", activeEngineList.get(1)));
            allTeamList.get(5).setAttributes(74, 78, 70);
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(13), "5", 0));
            //Hinch 5
            //allCarList.add(allTeamList.get(5).createCar(allDriverList.get(22), "6", 0));
            //Wickens
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(67), "7", 0));
            //Ericsson 7
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(11), "60", 1));
            //Harvey 60

            allTeamList.add(new Team("Ed Carpenter Racing", activeEngineList.get(0)));
            allTeamList.get(6).setAttributes(63, 75, 65);
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(4), "20", 3));
            //Carpenter 20
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(5), "20", 2));
            //Jones 20
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(29), "21", 0));
            //Pigot 21
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(5), "64", 5));
            //Jones 63 Indy only

            allTeamList.add(new Team("A.J. Foyt Enterprises", activeEngineList.get(0)));
            allTeamList.get(7).setAttributes(64, 69, 60);
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(41), "4", 0));
            //Leist 4
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(31), "14", 0));
            //Kanaan 14

            allTeamList.add(new Team("Harding Steinbrenner Racing", activeEngineList.get(0)));
            allTeamList.get(8).setAttributes(65, 69, 50);
            allCarList.add(allTeamList.get(8).createCar(allDriverList.get(48), "88", 0));
            //88 Herta
            *//*allCarList.add(allTeamList.get(8).createCar(allDriverList.get(TBA), "8", 0));
            //8 TBA*//*

            allTeamList.add(new Team("Carlin", activeEngineList.get(0)));
            allTeamList.get(9).setAttributes(64, 60, 55);
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(2), "23", 1));
            //23 Kimball
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(47), "31", 1));
            //37 O'Ward
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(18), "59", 0));
            //59 Chilton

            allTeamList.add(new Team("Juncos Racing", activeEngineList.get(0)));
            allTeamList.get(10).setAttributes(50, 50, 50);
            allCarList.add(allTeamList.get(10).createCar(allDriverList.get(38), "32", 0));
            //32 Kaiser
*//*            allCarList.add(allTeamList.get(10).createCar(allDriverList.get(TBA), "TBA", 0));
            //TBA TBA*//*

            allTeamList.add(new Team("Dreyer & Reinbold Racing", activeEngineList.get(0)));
            allTeamList.get(11).setAttributes(45, 50, 40);
            allCarList.add(allTeamList.get(11).createCar(allDriverList.get(24), "24", 5));
            //24 karam

            allTeamList.add(new Team("McLaren Racing", activeEngineList.get(0)));
            allTeamList.get(12).setAttributes(78, 80, 75);
            allCarList.add(allTeamList.get(12).createCar(allDriverList.get(51), "66", 5));
            //66 Alonso

            allTeamList.add(new Team("Clauson-Marshall Racing", activeEngineList.get(0)));
            allTeamList.get(13).setAttributes(45, 45, 45);
            allCarList.add(allTeamList.get(13).createCar(allDriverList.get(21), "39", 5));
            //49 Mann

            allTeamList.add(new Team("DragonSpeed", activeEngineList.get(0)));
            allTeamList.get(13).setAttributes(45, 45, 45);
            allCarList.add(allTeamList.get(13).createCar(allDriverList.get(54), "81", 1));
            //81 Hanley
*/
       activeEngineList = SheetsQuickstart.setEngineStatsByYear(allEngineList, year);
       SheetsQuickstart.setTeamStatsByYear(allTeamList, year, activeEngineList);

       allCarList = SheetsQuickstart.getCarList(year, allDriverList, allTeamList);
        //allTeamList.add(new Team("Team Penske", activeEngineList.get(0)));
//        allCarList.add(allTeamList.get(15).createCar(allDriverList.get(15), "2", EntryType.FULL));
//        //Newgarden 2
//        allCarList.add(allTeamList.get(15).createCar(allDriverList.get(9), "3", EntryType.MAY));
//        //Castroneves 3
//        allCarList.add(allTeamList.get(15).createCar(allDriverList.get(33), "12", EntryType.FULL));
//        //Power 12
//        allCarList.add(allTeamList.get(15).createCar(allDriverList.get(28), "22", EntryType.FULL));
//        //Pagenaud 22
//
//
//        //allTeamList.add(new Team("Chip Ganassi Racing", activeEngineList.get(1)));
//        //allTeamList.get(1).setAttributes(75, 68, 89);
//        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(25), "9", EntryType.FULL));
//        //Dixon 9
//        allCarList.add(allTeamList.get(4).createCar(allDriverList.get(36), "10", EntryType.FULL));
//        //Rosenqvist 10
//
//        //allTeamList.add(new Team("Andretti Autosport", activeEngineList.get(1)));
//        //allTeamList.get(2).setAttributes(75, 82, 75);
//        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(3), "25", EntryType.INDY_500));
//        //Daly 25
//        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(34), "26", EntryType.FULL));
//        //Veach 26
//        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(0), "27", EntryType.FULL));
//        //Rossi 27
//        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(23), "28", EntryType.FULL));
//        //Hunter-Reay 28
//        allCarList.add(allTeamList.get(1).createCar(allDriverList.get(17), "98", EntryType.FULL));
//        //Marco 98
//
//        //allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeEngineList.get(1)));
//        //allTeamList.get(3).setAttributes(72, 72, 68);
//        allCarList.add(allTeamList.get(14).createCar(allDriverList.get(8), "15", EntryType.FULL));
//        //Rahal 15
//        allCarList.add(allTeamList.get(14).createCar(allDriverList.get(30), "30", EntryType.FULL));
//        //Sato 30
//        allCarList.add(allTeamList.get(14).createCar(allDriverList.get(46), "42", EntryType.INDY_500));
//        //King 42
//
//        //allTeamList.add(new Team("Dale Coyne Racing", activeEngineList.get(1)));
//        //allTeamList.get(4).setAttributes(70, 55, 70);
//        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(26), "18", EntryType.FULL));
//        //Bourdais 18
//        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(50), "19", EntryType.FULL));
//        //Ferrucci 19
//        allCarList.add(allTeamList.get(6).createCar(allDriverList.get(12), "33", EntryType.INDY_500));
//        //Davison 33
//
//        //allTeamList.add(new Team("Arrow Schmidt Peterson Motorsports", activeEngineList.get(1)));
//        //allTeamList.get(5).setAttributes(74, 78, 70);
//        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(13), "5", EntryType.FULL));
//        //Hinch 5
//        //allCarList.add(allTeamList.get(2).createCar(allDriverList.get(22), "6", 0));
//        //Wickens
//        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(67), "7", EntryType.FULL));
//        //Ericsson 7
//        allCarList.add(allTeamList.get(13).createCar(allDriverList.get(11), "60", EntryType.HALF));
//        //Harvey 60
//        allCarList.add(allTeamList.get(2).createCar(allDriverList.get(20), "77", EntryType.INDY_500));
//        //Servia 77
//        //allTeamList.add(new Team("Ed Carpenter Racing", activeEngineList.get(0)));
//        //allTeamList.get(6).setAttributes(63, 75, 65);
//        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(4), "20", EntryType.OVAL));
//        //Carpenter 20
//        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(5), "20", EntryType.ROAD_STREET));
//        //Jones 20
//        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(29), "21", EntryType.FULL));
//        //Pigot 21
//        allCarList.add(allTeamList.get(9).createCar(allDriverList.get(5), "63", EntryType.INDY_500));
//        //Jones 63 Indy only
//
//        //allTeamList.add(new Team("A.J. Foyt Enterprises", activeEngineList.get(0)));
//        //allTeamList.get(7).setAttributes(64, 69, 60);
//        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(41), "4", EntryType.FULL));
//        //Leist 4
//        allCarList.add(allTeamList.get(0).createCar(allDriverList.get(31), "14", EntryType.FULL));
//        //Kanaan 14
//
//        //allTeamList.add(new Team("Harding Steinbrenner Racing", activeEngineList.get(0)));
//        //allTeamList.get(8).setAttributes(65, 69, 50);
//        allCarList.add(allTeamList.get(10).createCar(allDriverList.get(48), "88", EntryType.FULL));
//        //88 Herta
//           // *//*allCarList.add(allTeamList.get(8).createCar(allDriverList.get(TBA), "8", 0));
//        //8 TBA*//*
//
//        //allTeamList.add(new Team("Carlin", activeEngineList.get(0)));
//        //allTeamList.get(9).setAttributes(64, 60, 55);
//        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(2), "23", EntryType.HALF));
//        //23 Kimball
//        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(47), "31", EntryType.HALF));
//        //31 O'Ward
//        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(18), "59", EntryType.ROAD_STREET));
//        //59 Chilton
//        allCarList.add(allTeamList.get(3).createCar(allDriverList.get(18), "59", EntryType.INDY_500));
//        //59 Chilton indy 500
//
//        //allTeamList.add(new Team("Juncos Racing", activeEngineList.get(0)));
//        //allTeamList.get(10).setAttributes(50, 50, 50);
//        allCarList.add(allTeamList.get(11).createCar(allDriverList.get(38), "32", EntryType.HALF));
//        //32 Kaiser
////          allCarList.add(allTeamList.get(10).createCar(allDriverList.get(TBA), "TBA", 0));
//        //TBA TBA*//*
//
//        //allTeamList.add(new Team("Dreyer & Reinbold Racing", activeEngineList.get(0)));
//        //allTeamList.get(11).setAttributes(45, 50, 40);
//        allCarList.add(allTeamList.get(8).createCar(allDriverList.get(24), "24", EntryType.INDY_500));
//        //24 karam
//        allCarList.add(allTeamList.get(8).createCar(allDriverList.get(10), "48", EntryType.INDY_500));
//
//        //allTeamList.add(new Team("McLaren Racing", activeEngineList.get(0)));
//        //allTeamList.get(12).setAttributes(78, 80, 75);
//        allCarList.add(allTeamList.get(12).createCar(allDriverList.get(51), "66", EntryType.INDY_500));
//        //66 Alonso
//
//        //allTeamList.add(new Team("Clauson-Marshall Racing", activeEngineList.get(0)));
//        //allTeamList.get(13).setAttributes(45, 45, 45);
//        allCarList.add(allTeamList.get(5).createCar(allDriverList.get(21), "39", EntryType.INDY_500));
//        //49 Mann
//
//        //allTeamList.add(new Team("DragonSpeed", activeEngineList.get(0)));
//        //allTeamList.get(13).setAttributes(45, 45, 45);
//        allCarList.add(allTeamList.get(7).createCar(allDriverList.get(54), "81", EntryType.HALF));
        //81 Hanley

        /*else if (year == 2020) {
            allTeamList.add(new Team("Team Penske", activeEngineList.get(0)));
            allTeamList.get(0).setAttributes(79, 76, 82);
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(15), "2", 0));
            //Newgarden 2
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(9), "3", 4));
            //Castroneves 3
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(33), "12", 0));
            //Power 12
            allCarList.add(allTeamList.get(0).createCar(allDriverList.get(8), "22", 0));
            //Rahal 22


            allTeamList.add(new Team("Chip Ganassi Racing", activeEngineList.get(1)));
            allTeamList.get(1).setAttributes(75, 68, 89);
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(25), "9", 0));
            //Dixon 9
            allCarList.add(allTeamList.get(1).createCar(allDriverList.get(36), "10", 0));
            //Rosenqvist 10
            //

            allTeamList.add(new Team("Andretti Autosport", activeEngineList.get(1)));
            allTeamList.get(2).setAttributes(76, 80, 75);
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(44), "25", 5));
            //Wilson 25
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(34), "26", 0));
            //Veach 26
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(0), "27", 0));
            //Rossi 27
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(23), "28", 0));
            //Hunter-Reay 28
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(47), "29", 4));
            //O'Ward 29
            allCarList.add(allTeamList.get(2).createCar(allDriverList.get(48), "98", 0));
            //Herta 98

            allTeamList.add(new Team("Rahal Letterman Lanigan Racing", activeEngineList.get(1)));
            allTeamList.get(3).setAttributes(72, 74, 68);
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(46), "15", 0));
            //King 15
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(30), "30", 0));
            //Sato 30
            allCarList.add(allTeamList.get(3).createCar(allDriverList.get(20), "64", 5));
            //Servia 64

            allTeamList.add(new Team("Dale Coyne Racing", activeEngineList.get(1)));
            allTeamList.get(4).setAttributes(70, 65, 70);
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(26), "18", 0));
            //Bourdais 18
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(5), "19", 0));
            //Jones 19
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(24), "17", 1));
            //Karam 17
            allCarList.add(allTeamList.get(4).createCar(allDriverList.get(21), "63", 5));
            //Pippa 63

            allTeamList.add(new Team("Schmidt Peterson Motorsports", activeEngineList.get(1)));
            allTeamList.get(5).setAttributes(77, 78, 70);
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(13), "5", 0));
            //Hinch 5
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(22), "6", 0));
            //Wickens 6
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(14), "7", 5));
            //Howard 7
            allCarList.add(allTeamList.get(5).createCar(allDriverList.get(39), "60", 1));
            //Urrutia 60

            allTeamList.add(new Team("Ed Carpenter Racing", activeEngineList.get(0)));
            allTeamList.get(6).setAttributes(70, 75, 65);
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(4), "24", 5));
            //Carpenter 24
            //allCarList.add(allTeamList.get(6).createCar(allDriverList.get(46), "20", 0));
            //King 20
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(29), "21", 0));
            //Pigot 21
            allCarList.add(allTeamList.get(6).createCar(allDriverList.get(17), "20", 0));
            //Marco 20

            allTeamList.add(new Team("A.J. Foyt Enterprises", activeEngineList.get(0)));
            allTeamList.get(7).setAttributes(67, 71, 60);
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(41), "4", 0));
            //Leist 4
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(31), "14", 0));
            //Kanaan 14
            allCarList.add(allTeamList.get(7).createCar(allDriverList.get(12), "33", 5));
            //Davison 33

            allTeamList.add(new Team("Harding Racing", activeEngineList.get(0)));
            allTeamList.get(8).setAttributes(50, 65, 50);
            allCarList.add(allTeamList.get(8).createCar(allDriverList.get(7), "88", 1));
            //88 chaves
            allCarList.add(allTeamList.get(8).createCar(allDriverList.get(35), "8", 0));
            //claman de melo

            allTeamList.add(new Team("Carlin", activeEngineList.get(0)));
            allTeamList.get(9).setAttributes(70, 63, 55);
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(2), "23", 1));
            //23 Kimball
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(18), "59", 0));
            //59 Chilton
            allCarList.add(allTeamList.get(9).createCar(allDriverList.get(1), "11", 0));
            //munoz

            allTeamList.add(new Team("Juncos Racing", activeEngineList.get(0)));
            allTeamList.get(10).setAttributes(50, 50, 50);
            allCarList.add(allTeamList.get(10).createCar(allDriverList.get(38), "32", 0));
            //32 Kaiser/Bender
            allCarList.add(allTeamList.get(10).createCar(allDriverList.get(45), "33", 0));

            //allTeamList.add(new Team("Dreyer & Reinbold Racing", activeEngineList.get(0)));
            //allTeamList.get(11).setAttributes(45, 50, 40);
            //allCarList.add(allTeamList.get(11).createCar(allDriverList.get(24), "24", 1));
            //24 karam

            allTeamList.add(new Team("McLaren-Carpenter", activeEngineList.get(0)));
            allTeamList.get(11).setAttributes(78, 80, 75);
            allCarList.add(allTeamList.get(11).createCar(allDriverList.get(28), "40", 0));
            //40 Pagenaud
            allCarList.add(allTeamList.get(11).createCar(allDriverList.get(51), "41", 0));
            //41 Alonso
            allCarList.add(allTeamList.get(11).createCar(allDriverList.get(10), "42", 3));
            //42 Hildebrand

            allTeamList.add(new Team("Boleyn Racing", activeEngineList.get(1)));
            allTeamList.get(12).setAttributes(65, 69, 70);
            allCarList.add(allTeamList.get(12).createCar(allDriverList.get(19), "90", 0));
            //Aleshin
            allCarList.add(allTeamList.get(12).createCar(allDriverList.get(3), "95", 0));
            //Daly
            allCarList.add(allTeamList.get(12).createCar(allDriverList.get(6), "91", 2));
            //Gutierrez
            //allCarList.add(allTeamList.get(13).createCar(allDriverList.get(40),"91",0));
            //Fittipaldi
            allCarList.add(allTeamList.get(12).createCar(allDriverList.get(27), "91", 3));
            //saavedra
        }*/

        possibleEntries.clear();
        possibleEntries.addAll(allCarList);
    }
}
