package com.napier.devops.controller;

import com.napier.devops.entity.CountryReport;
import com.napier.devops.service.CountryReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin()
@RestController
@RequestMapping("/api/countries")
public class CountryReportController {
    private final CountryReportService service;

    public CountryReportController(CountryReportService service) {
        this.service = service;
    }

    @RequestMapping("/world")
    public ArrayList<CountryReport> getCountryInWorldController(@RequestParam(value = "n", required = false) Integer n) {
        // Pass the Integer N to the service layer
        return service.getCountriesInWorldService(n);
    }

    @RequestMapping("/continent")
    public ArrayList<CountryReport> getCountryInContinentController(@RequestParam(value = "continent") String continent, @RequestParam(value = "n", required = false) Integer n) {
        return service.getCountriesInContinentService(continent, n);
    }

    @RequestMapping("/region")
    public ArrayList<CountryReport> getCountryInRegionController(@RequestParam(value = "region") String region, @RequestParam(value = "n", required = false) Integer n) {
        return service.getCountriesInRegionService(region, n);
    }
}
