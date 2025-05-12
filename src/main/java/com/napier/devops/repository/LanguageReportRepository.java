package com.napier.devops.repository;

import com.napier.devops.entity.LanguageReport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class LanguageReportRepository {
    private final DataSource dataSource;

    public LanguageReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<LanguageReport> getLanguageSpeakersData() {
        String query = """
        SELECT lang.Language,
               SUM(ROUND((ctry.Population * lang.Percentage) / 100)) AS Total_Speakers,
               ROUND(SUM(ROUND((ctry.Population * lang.Percentage) / 100)) * 100 / 
                     (SELECT SUM(ctry.Population) FROM country ctry), 2) AS World_Percentage
        FROM countrylanguage lang
        JOIN country ctry ON lang.CountryCode = ctry.Code
        WHERE lang.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
        GROUP BY lang.Language
        ORDER BY Total_Speakers DESC;
        """;

        ArrayList<LanguageReport> languageReports = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query);
             ResultSet rset = prepStmt.executeQuery()) {

            // System.out.println("Query executed successfully!");

            while (rset.next()) {
                LanguageReport languageReport = new LanguageReport();
                languageReport.setLanguage(rset.getString("Language"));
                languageReport.setTotalSpeakers(rset.getLong("Total_Speakers"));
                languageReport.setWorldPercentage(rset.getFloat("World_Percentage"));
                languageReports.add(languageReport);

                // Log the data for debugging
                // System.out.println(languageReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve language data.");
        }
        return languageReports;
    }
}
