package com.napier.devops.service;

import com.napier.devops.entity.LanguageReport;
import com.napier.devops.repository.LanguageReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LanguageReportService {
    private final LanguageReportRepository repository;

    public LanguageReportService(LanguageReportRepository repository) {
        this.repository = repository;
    }

    public ArrayList<LanguageReport> fetchLanguageReports() {
        return repository.getLanguageSpeakersData();
    }
}
