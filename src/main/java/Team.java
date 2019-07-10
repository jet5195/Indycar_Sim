import java.util.List;

public class Team {
    private String name;
    private String nickname;
    private boolean active;
    private List<Car> cars;
    private Engine engine;
    private int road;
    private int street;
    private int smallOval;
    private int largeOval;
    private int pitCrew;
    private int reliability;

    public Team(String name, String nickname){
        this.name = name;
        this.nickname = nickname;
    }

    public void setAttributes(boolean active, Engine engine, int road, int street, int smallOval, int largeOval, int pitCrew, int reliability){
        this.active = active;
        this.engine = engine;
        this.road = road;
        this.street = street;
        this.smallOval = smallOval;
        this.largeOval = largeOval;
        this.pitCrew = pitCrew;
        this.reliability = reliability;
    }

    public Car createCar(Driver driver, String number, int fullTime){
        Car newCar = new Car(driver, number, this.name, fullTime, this.road, this.street, this.smallOval, this.largeOval, this.pitCrew);
        return newCar;
    }

}
