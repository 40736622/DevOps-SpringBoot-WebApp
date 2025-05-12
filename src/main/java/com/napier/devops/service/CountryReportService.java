package com.napier.devops.service;

import com.napier.devops.entity.CountryReport;
import com.napier.devops.repository.CountryReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CountryReportService {
    private final CountryReportRepository repository;

    public CountryReportService(CountryReportRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CountryReport> getCountriesInWorldService(Integer n) {
        return repository.getCountriesInWorld(n);
    }

    public ArrayList<CountryReport> getCountriesInContinentService(String continent, Integer n) {
        return repository.getCountriesInContinent(continent, n);
    }

    public ArrayList<CountryReport> getCountriesInRegionService(String region, Integer n) {
        return repository.getCountriesInRegion(region, n);
    }
}
