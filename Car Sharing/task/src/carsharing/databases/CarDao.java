package carsharing.databases;

import java.util.List;

public interface CarDao {
    List<Car> getAllCarList(Company company);

    void addCar(Car car);

    Car getCar(int id);
}
