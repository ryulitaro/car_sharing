package carsharing.view;

import carsharing.databases.*;

import java.util.List;
import java.util.Scanner;

public class CustomerView {
    static final List<String> customerMenu = List.of("1. Rent a car", "2. Return a rented car", "3. My rented car", "0. Back");

    static void newLine() {
        System.out.println();
    }

    static int getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void startCreateCustomer(CustomerDao customerDao) {
        System.out.println("Enter the customer name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();
        Customer customer = new Customer(name);
        customerDao.addCustomer(customer);
        System.out.println("The customer was added!");
        newLine();
    }

    public static void startCustomerListMenu(CustomerDao customerDao, CarDao carDao, CompanyDao companyDao) {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }
        int input = -1;
        while (input != 0) {
            System.out.println("Choose a customer:");
            for (int i = 1; i <= customers.size(); i++) {
                System.out.printf("%d. %s%n", i, customers.get(i - 1).getName());
            }
            System.out.println("0. Back");
            input = getInput();
            newLine();
            if (input > 0 && input <= customers.size()) {
                Customer customer = customers.get(input - 1);
                startCustomerMenu(customer, customerDao, carDao, companyDao);
            }
        }
    }

    static void startCustomerMenu(Customer customer, CustomerDao customerDao, CarDao carDao, CompanyDao companyDao) {
        int input = -1;
        while (input != 0) {
            customerMenu.forEach(System.out::println);
            input = getInput();
            newLine();
            if (input == 1) {
                rentCar(customer, customerDao, companyDao, carDao);
            } else if (input == 2) {
                showReturnCar(customer, customerDao);
            } else if (input == 3) {
                showRentedCar(customer, carDao, companyDao);
            }
        }
    }

    static void rentCar(Customer customer, CustomerDao customerDao, CompanyDao companyDao, CarDao carDao) {
        if (customer.getRentedCarId() > 0) {
            System.out.println("You've already rented a car!");
            return;
        }
        startCompanyListMenu(customer, customerDao, companyDao, carDao);
    }

    static void startCompanyListMenu(Customer customer, CustomerDao customerDao, CompanyDao companyDao, CarDao carDao) {
        List<Company> companies = companyDao.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose a company:");
        for (int i = 1; i <= companies.size(); i++) {
            System.out.printf("%d. %s%n", i, companies.get(i - 1).getName());
        }
        System.out.println("0. Back");
        int input = getInput();
        newLine();
        if (input > 0 && input <= companies.size()) {
            Company company = companies.get(input - 1);
            startCarListMenu(customer, customerDao, company, carDao);
        }
    }

    static void startCarListMenu(Customer customer, CustomerDao customerDao, Company company, CarDao carDao) {
        List<Car> cars = carDao.getAllCarList(company);
        if (cars.isEmpty()) {
            System.out.println("No available cars in the '" + company.getName() + "' company");
            return;
        }
        System.out.println("Choose a car:");
        for (int i = 1; i <= cars.size(); i++) {
            System.out.printf("%d. %s%n", i, cars.get(i - 1).getName());
        }
        System.out.println("0. Back");
        int input = getInput();
        newLine();
        if (input > 0 && input <= cars.size()) {
            showRentedCar(customer, customerDao, cars.get(input - 1));
        }
    }

    static void showRentedCar(Customer customer, CustomerDao customerDao, Car car) {
        customer.setRentedCarId(car.getId());
        customerDao.updateCustomer(customer);
        System.out.println("You rented '" + car.getName() + "'");
        newLine();
    }

    static void showReturnCar(Customer customer, CustomerDao customerDao) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        customer.resetRentedCarId();
        customerDao.updateCustomer(customer);
        System.out.println("You've returned a rented car!");
    }

    static void showRentedCar(Customer customer, CarDao carDao, CompanyDao companyDao) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        Car car = carDao.getCar(customer.getRentedCarId());
        Company company = companyDao.getCompany(car.getCompanyId());
        System.out.println("Your rented car:");
        System.out.println(car.getName());
        System.out.println("Company:");
        System.out.println(company.getName());
        newLine();
    }
}
