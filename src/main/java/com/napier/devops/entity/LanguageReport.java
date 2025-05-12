package com.napier.devops.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class LanguageReport {
    private String language;
    private long totalSpeakers;
    private float worldPercentage;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getTotalSpeakers() {
        return totalSpeakers;
    }

    public void setTotalSpeakers(long totalSpeakers) {
        this.totalSpeakers = totalSpeakers;
    }

    public float getWorldPercentage() {
        return worldPercentage;
    }

    public void setWorldPercentage(float worldPercentage) {
        this.worldPercentage = worldPercentage;
    }

    /**
     *
     * @return LanguageReport object instance as a string.
     */
    @Override
    public String toString() {
        return String.format("LanguageReport{Language = %s, Total_Speakers = %s, World_Percentage = %.2f%%}",
                getLanguage() != null ? getLanguage() : "N/A",
                NumberFormat.getInstance().format(getTotalSpeakers()),
                getWorldPercentage());
    }

    /**
     * Prints a list of language data.
     *
     * @param languages The list of language data to print.
     */
    public void displayLanguages(ArrayList<LanguageReport> languages) {
        // Check if languages list is empty
        if (languages == null || languages.isEmpty()) {
            System.out.println("No language data found!");
            return;
        }

        // Print header
        System.out.printf("%-20s %-25s %-15s%n", "Language", "Total Speakers", "World Percentage");
        for (LanguageReport language : languages) {
            if (language == null) {
                continue;
            }
            String languageString = String.format("%-20s %-25s %-15s",
                    language.getLanguage(),
                    NumberFormat.getInstance().format(language.getTotalSpeakers()),
                    language.getWorldPercentage() + "%");
            System.out.println(languageString);
        }
    }
}