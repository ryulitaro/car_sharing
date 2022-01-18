package carsharing.databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    Connection connection;

    public CarDaoImpl(DataBase dataBase) {
        this.connection = dataBase.getConnection();
    }

    @Override
    public List<Car> getAllCarList(Company company) {
        List<Car> cars = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM CAR WHERE COMPANY_ID=" + company.getId() + " ORDER BY ID";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int company_id = resultSet.getInt("COMPANY_ID");
                cars.add(new Car(id, name, company_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void addCar(Car car) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO CAR(NAME, COMPANY_ID) " +
                    "VALUES ('" + car.getName() + "', " + car.getCompanyId() + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCar(int id) {
        Car car = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM CAR WHERE ID=" + id;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int company_id = resultSet.getInt("COMPANY_ID");
                car = new Car(id, name, company_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
}
