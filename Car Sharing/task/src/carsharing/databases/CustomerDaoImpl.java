package carsharing.databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    Connection connection;

    public CustomerDaoImpl(DataBase dataBase) {
        this.connection = dataBase.getConnection();
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO CUSTOMER(NAME) " +
                    "VALUES ('" + customer.getName() + "')";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM CUSTOMER ORDER BY ID";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int rented_car_id = resultSet.getInt("RENTED_CAR_ID");
                customerList.add(new Customer(id, name, rented_car_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            Statement stmt = connection.createStatement();
            String rentedCarId = customer.getRentedCarId() == 0 ? "NULL" : String.valueOf(customer.getRentedCarId());
            String sql = "UPDATE CUSTOMER " +
                    "SET RENTED_CAR_ID = " + rentedCarId +
                    " WHERE ID = " + customer.getId();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
