package carsharing.databases;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db";
    private String name = "test";
    private String filename;
    private File directory = new File("./src/carsharing/db");
    private Connection connection;

    public DataBase(String filename) {
        this.filename = filename;
        directory.mkdirs();
    }

    public Connection getConnection() {
        return connection;
    }

    public void create() {
        generateConnection();
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    void generateConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + File.separator + filename);
            connection.setAutoCommit(true);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    void createCompanyTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS COMPANY(" +
                    "ID INTEGER NOT NULL, " +
                    "NAME VARCHAR, " +
                    "PRIMARY KEY (ID))";
            stmt.executeUpdate(sql);
            sql = "ALTER TABLE COMPANY " +
                    "ALTER ID INT AUTO_INCREMENT";
            stmt.executeUpdate(sql);
            sql = "ALTER TABLE COMPANY " +
                    "ALTER NAME VARCHAR NOT NULL";
            stmt.executeUpdate(sql);
            sql = "ALTER TABLE COMPANY " +
                    "ADD UNIQUE (NAME)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
        }
    }

    void createCarTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CAR(" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "COMPANY_ID INTEGER NOT NULL, " +
                    "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID) " +
                    "REFERENCES COMPANY (ID))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
        }
    }

    void createCustomerTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "RENTED_CAR_ID INTEGER, " +
                    "CONSTRAINT fk_car FOREIGN KEY (RENTED_CAR_ID) " +
                    "REFERENCES CAR (ID))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
