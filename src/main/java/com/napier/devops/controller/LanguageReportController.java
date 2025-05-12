package com.napier.devops.controller;

import com.napier.devops.entity.LanguageReport;
import com.napier.devops.service.LanguageReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class LanguageReportController {
    private final LanguageReportService service;

    public LanguageReportController(LanguageReportService service) {
        this.service = service;
    }

    @GetMapping("/api/languages")
    public ArrayList<LanguageReport> getLanguageReports() {
        return service.fetchLanguageReports();
    }
}
