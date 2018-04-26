import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Team {
    private String name;
    private List<Car> cars;
    private Manufacturer manufacturer;
    private int road;
    private int oval;
    private int pitCrew;

    public Team(String name, Manufacturer manufacturer){
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public void setAttributes(int road, int oval, int pitCrew){
        this.road = road;
        this.oval = oval;
        this.pitCrew = pitCrew;
    }

    public Car createCar(Driver driver, String number, int fullTime){
        Car newCar = new Car(driver, number, this.name, fullTime);
        return newCar;
    }

}
