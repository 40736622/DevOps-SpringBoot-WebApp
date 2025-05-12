package com.napier.devops.entity;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Represents a country report in the system.
 * This class stores report information such as code, name, continent, region, population and capital.
 */
public class CountryReport extends Report {
    private String code;
    private String continent;
    private String region;
    private String capital;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**
     *
     * @return CountryReport object instance as a string.
     */
    @Override
    public String toString() {
        return String.format("CountryReport{Code = %s, Name = %s, Continent = %s, Region = %s, Population = %s, Capital = %s}",
                getCode() != null ? getCode() : "N/A",
                getName() != null ? getName() : "N/A",
                getContinent() != null ? getContinent() : "N/A",
                getRegion() != null ? getRegion() : "N/A",
                NumberFormat.getInstance().format(getPopulation()),
                getCapital() != null ? getCapital() : "N/A");
    }

    /**
     * Prints a list of countries.
     *
     * @param countries The list of countries to print.
     */
    public void displayCountries(ArrayList<CountryReport> countries) {
        // Check countries is not null
        if (countries == null || countries.isEmpty())
        {
            System.out.println("No countries found!");
            return;
        }

        // Print header
        System.out.printf("%-5s %-48s %-15s %-28s %20s %10s%n", "Code", "Name", "Continent", "Region", "Population", "Capital");
        // Loop over all countries in the list
        for (CountryReport country : countries) {
            if (country == null) {
                continue;
            }
            String country_string =
                    String.format("%-5s %-48s %-15s %-28s %20s %10s",
                            country.getCode(), country.getName(), country.getContinent(), country.getRegion(), NumberFormat.getInstance().format(country.getPopulation()), country.getCapital() != null ? country.getCapital() : "N/A");
            System.out.println(country_string);
        }
    }
}


