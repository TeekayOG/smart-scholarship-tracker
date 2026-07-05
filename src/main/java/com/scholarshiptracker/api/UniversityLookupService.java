package com.scholarshiptracker.api;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Provides safe university lookup behaviour for the GUI.
 */
public class UniversityLookupService {

    private final UniversityApiClient apiClient;

    public UniversityLookupService() {
        this(new UniversityApiClient());
    }

    public UniversityLookupService(UniversityApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Searches universities and converts API failures into user-friendly messages.
     *
     * @param country country entered by the user
     * @return lookup result containing universities or a safe error message
     */
    public UniversityLookupResult searchUniversities(String country) {
        if (country == null || country.trim().isEmpty()) {
            return new UniversityLookupResult(
                    false,
                    "Country name is required before searching university data.",
                    Collections.emptyList()
            );
        }

        try {
            List<University> universities = apiClient.fetchUniversitiesByCountry(country.trim());

            if (universities.isEmpty()) {
                return new UniversityLookupResult(
                        false,
                        "No university records were found for the selected country.",
                        Collections.emptyList()
                );
            }

            return new UniversityLookupResult(
                    true,
                    "University data loaded successfully.",
                    universities
            );
        } catch (IOException error) {
            return new UniversityLookupResult(
                    false,
                    "University data is temporarily unavailable. Please try again later.",
                    Collections.emptyList()
            );
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            return new UniversityLookupResult(
                    false,
                    "University search was interrupted. Please try again.",
                    Collections.emptyList()
            );
        }
    }
}
