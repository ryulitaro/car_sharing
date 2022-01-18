package carsharing.view;

import carsharing.databases.Car;
import carsharing.databases.CarDao;
import carsharing.databases.Company;
import carsharing.databases.CompanyDao;

import java.util.List;
import java.util.Scanner;

public class ManagerView {
    static final List<String> managerMenu = List.of("1. Company list", "2. Create a company", "0. Back");
    static final List<String> companyMenu = List.of("1. Car list", "2. Create a car", "0. Back");

    static void newLine() {
        System.out.println();
    }

    static int getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void startManagerMenu(CompanyDao companyDao, CarDao carDao) {
        int input = -1;
        while (input != 0) {
            managerMenu.forEach(System.out::println);
            input = getInput();
            newLine();
            if (input == 1) {
                startCompanyListMenu(companyDao, carDao);
                newLine();
            } else if (input == 2) {
                showCreateCompany(companyDao);
                newLine();
            }
        }
    }


    static void startCompanyListMenu(CompanyDao companyDao, CarDao carDao) {
        List<Company> companies = companyDao.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose the company:");
        for (int i = 1; i <= companies.size(); i++) {
            System.out.printf("%d. %s%n", i, companies.get(i - 1).getName());
        }
        System.out.println("0. Back");
        int input = getInput();
        newLine();
        if (input > 0 && input <= companies.size()) {
            Company company = companies.get(input - 1);
            startCompanyMenu(company, carDao);
        }
    }

    static void startCompanyMenu(Company company, CarDao carDao) {
        System.out.println("'" + company.getName() + "' company:");
        int input = -1;
        while (input != 0) {
            companyMenu.forEach(System.out::println);
            input = getInput();
            newLine();
            if (input == 1) {
                startCarListMenu(company, carDao);
            } else if (input == 2) {
                showCreateCar(company, carDao);
            }
        }
    }

    static void showCreateCar(Company company, CarDao carDao) {
        System.out.println("Enter the car name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();
        carDao.addCar(new Car(name, company.getId()));
        System.out.println("The car was added!");
        newLine();
    }

    static void startCarListMenu(Company company, CarDao carDao) {
        List<Car> cars = carDao.getAllCarList(company);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("'" + company.getName() + "' cars:");
            for (int i = 1; i <= cars.size(); i++) {
                System.out.printf("%d. %s%n", i, cars.get(i - 1).getName());
            }
            newLine();
        }
    }

    static void showCreateCompany(CompanyDao companyDao) {
        System.out.println("Enter the company name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();
        companyDao.addCompany(new Company(name));
        System.out.println("The company was created!");
    }
}
