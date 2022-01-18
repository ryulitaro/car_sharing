package carsharing;

import carsharing.databases.*;
import carsharing.view.CustomerView;
import carsharing.view.ManagerView;

import java.util.List;
import java.util.Scanner;


public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final List<String> initMenu = List.of("1. Log in as a manager",
            "2. Log in as a customer",
            "3. Create a customer",
            "0. Exit");

    public static void main(String[] args) {
        // 1. create database file
        String filename = getDatabaseName(args);
        DataBase dataBase = new DataBase(filename);
        // 2. connect to database and create tables
        dataBase.create();
        // 3. create company dao, car dao with db connection
        CompanyDao companyDao = new CompanyDaoImpl(dataBase);
        CarDao carDao = new CarDaoImpl(dataBase);
        CustomerDao customerDao = new CustomerDaoImpl(dataBase);
        // 4. start menu
        startInitMenu(companyDao, carDao, customerDao);
        // 5. close db connection
        dataBase.closeConnection();
        System.out.println("Goodbye!");
    }

    static String getDatabaseName(String[] args) {
        String filename = "test";
        if (args.length == 2 && args[0].equals("-databaseFileName")) {
            filename = args[1];
        }
        return filename;
    }

    static void startInitMenu(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        int input = -1;
        while (input != 0) {
            initMenu.forEach(System.out::println);
            input = scanner.nextInt();
            System.out.println();
            if (input == 1) {
                ManagerView.startManagerMenu(companyDao, carDao);
            } else if (input == 2) {
                CustomerView.startCustomerListMenu(customerDao, carDao, companyDao);
            } else if (input == 3) {
                CustomerView.startCreateCustomer(customerDao);
            }
        }
    }
}