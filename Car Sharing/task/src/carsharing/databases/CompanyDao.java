package carsharing.databases;

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompanies();

    void addCompany(Company company);

    Company getCompany(int id);
}
