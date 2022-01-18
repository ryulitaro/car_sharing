package carsharing.databases;

import java.util.List;

public interface CustomerDao {
    public void addCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public void updateCustomer(Customer customer);
}
