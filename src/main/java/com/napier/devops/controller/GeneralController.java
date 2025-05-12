package com.napier.devops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import com.napier.devops.entity.CountryReport;
import java.util.List;
import java.util.Arrays;

@Controller
public class GeneralController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/country-world")
    public String countryWorld(Model model) {
        String apiUrl = "http://127.0.0.1:8080/api/countries/world"; // Replace with actual API
        RestTemplate restTemplate = new RestTemplate();
        CountryReport[] countryArray = restTemplate.getForObject(apiUrl, CountryReport[].class);

        List<CountryReport> countries = Arrays.asList(countryArray);
        model.addAttribute("countries", countries);

        return "country-world";
    }
}
