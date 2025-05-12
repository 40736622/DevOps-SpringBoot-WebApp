package com.napier.devops.repository;

import com.napier.devops.entity.CountryReport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CountryReportRepository {
    private final DataSource dataSource;

    public CountryReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Converts a result set row into a CountryReport object.
     *
     * @param rset Holds query result set containing country data.
     * @return A CountryReport instance.
     * @throws SQLException If SQL error occurs while accessing data.
     */
    public CountryReport mapToCountry(ResultSet rset) throws SQLException {
        CountryReport country = new CountryReport();
        country.setCode(rset.getString("ctry.Code"));
        country.setName(rset.getString("ctry.Name"));
        country.setContinent(rset.getString("ctry.Continent"));
        country.setRegion(rset.getString("ctry.Region"));
        country.setPopulation(rset.getLong("ctry.Population"));
        country.setCapital(rset.getString("Capital"));

        return country;
    }

    /**
     * Gets all the countries in the world, organized by population (largest to smallest), or the top N populated countries if N is not null.
     *
     * @param N The number of top populated countries to retrieve.
     * @return A list of all countries in the world, or null if there is an error.
     */
    public ArrayList<CountryReport> getCountriesInWorld(Integer N) {
        // SQL query to get all countries in the world, ordered by population
        String query = "SELECT ctry.Code, ctry.Name, ctry.Continent, ctry.Region, ctry.Population, cty.Name AS Capital "
                + "FROM country ctry "
                + "LEFT JOIN city cty ON ctry.Capital = cty.ID "
                + "ORDER BY ctry.Population DESC";

        if (N != null) {
            query += " LIMIT ?";
        }

        // Prepare the SQL statement
        try(Connection conn = dataSource.getConnection();
                PreparedStatement prepStmt = conn.prepareStatement(query)) {

            if (N != null) {
                prepStmt.setInt(1, N);
            }

            ResultSet rset = prepStmt.executeQuery();

            ArrayList<CountryReport> countries = new ArrayList<>();

            // Loop through the result set and create CountryReport objects
            while (rset.next()) {
                countries.add(mapToCountry(rset));
            }
            return countries;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve country details.");
            return null;
        }
    }

    // Using method overloading to set a default value of null for N.
    public ArrayList<CountryReport> getCountriesInWorld() {
        return getCountriesInWorld(null);
    }

    /**
     * Retrieves all the countries in a continent in descending order, or the top N populated countries if N is not null.
     *
     * @param continent The continent for which the top populated countries will be retrieved.
     * @param N The number of top populated countries to retrieve.
     * @return A list of countries in a continent, or null if there is an error.
     */
    public ArrayList<CountryReport> getCountriesInContinent(String continent, Integer N) {
        String query = "SELECT ctry.Code, ctry.Name, ctry.Continent, ctry.Region, ctry.Population, cty.Name As Capital "
                + "FROM country ctry "
                + "LEFT JOIN city cty ON ctry.Capital = cty.ID "
                + "WHERE Continent = ? "
                + "ORDER BY Population DESC";

        if (N != null) {
            query += " LIMIT ?";
        }

        try(Connection conn = dataSource.getConnection();
            PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setString(1, continent);

            if (N != null) {
                prepStmt.setInt(2, N);
            }

            ResultSet rset = prepStmt.executeQuery();

            ArrayList<CountryReport> countries = new ArrayList<>();

            while (rset.next()) {
                countries.add(mapToCountry(rset));
            }
            return countries;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve details.");
            return null;
        }
    }

    // Using method overloading to set a default value of null for N.
    public ArrayList<CountryReport> getCountriesInContinent(String continent) {
        return getCountriesInContinent(continent, null);
    }

    /**
     * Gets all the countries in a region, organized by population (largest to smallest), or the top N populated countries if N is not null.
     * @param region The region for which the top populated countries will be retrieved.
     * @param N      The number of top populated countries to retrieve.
     *
     * @return A list of all countries in a region, or null if there is an error.
     */
    public ArrayList<CountryReport> getCountriesInRegion(String region, Integer N) {
        // SQL query to get countries in a region, ordered by population
        String query = "SELECT ctry.Code, ctry.Name, ctry.Continent, ctry.Region, ctry.Population, cty.Name AS Capital "
                + "FROM country ctry "
                + "LEFT JOIN city cty ON ctry.Capital = cty.ID "
                + "WHERE ctry.Region = ? "
                + "ORDER BY ctry.Population DESC";

        if (N != null) {
            query += " LIMIT ?";
        }

        // Prepare the SQL statement with the region parameter
        try(Connection conn = dataSource.getConnection();
            PreparedStatement prepStmt = conn.prepareStatement(query)) {
            prepStmt.setString(1, region);

            if (N != null) {
                prepStmt.setInt(2, N);
            }

            ResultSet rset = prepStmt.executeQuery();

            ArrayList<CountryReport> countries = new ArrayList<>();

            // Loop through the result set and create CountryReport objects
            while (rset.next()) {
                countries.add(mapToCountry(rset));
            }
            return countries;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve details.");
            return null;
        }
    }

    // Using method overloading to set a default value of null for N.
    public ArrayList<CountryReport> getCountriesInRegion(String region) {
        return getCountriesInRegion(region, null);
    }
}
