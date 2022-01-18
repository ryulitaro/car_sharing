package carsharing.databases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    Connection connection;

    public CompanyDaoImpl(DataBase dataBase) {
        this.connection = dataBase.getConnection();
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM COMPANY ORDER BY ID";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    @Override
    public void addCompany(Company company) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO COMPANY(NAME) " +
                    "VALUES ('" + company.getName() + "')";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company getCompany(int id) {
        Company company = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM COMPANY WHERE ID=" + id;
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                company = new Company(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

}
