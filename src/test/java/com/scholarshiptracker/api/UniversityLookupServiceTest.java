package com.scholarshiptracker.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniversityLookupServiceTest {

    @Test
    void searchUniversities_withBlankCountry_returnsValidationMessage() {
        UniversityLookupService service = new UniversityLookupService(new StubUniversityApiClient(List.of()));

        UniversityLookupResult result = service.searchUniversities("   ");

        assertAll(
                () -> assertFalse(result.isSuccess()),
                () -> assertEquals("Country name is required before searching university data.", result.getMessage()),
                () -> assertTrue(result.getUniversities().isEmpty())
        );
    }

    @Test
    void searchUniversities_withNullCountry_returnsValidationMessage() {
        UniversityLookupService service = new UniversityLookupService(new StubUniversityApiClient(List.of()));

        UniversityLookupResult result = service.searchUniversities(null);

        assertFalse(result.isSuccess());
        assertEquals("Country name is required before searching university data.", result.getMessage());
    }

    @Test
    void searchUniversities_withApiResults_returnsSuccessResult() {
        University university = createUniversity();
        UniversityLookupService service = new UniversityLookupService(new StubUniversityApiClient(List.of(university)));

        UniversityLookupResult result = service.searchUniversities(" Malaysia ");

        assertAll(
                () -> assertTrue(result.isSuccess()),
                () -> assertEquals("University data loaded successfully.", result.getMessage()),
                () -> assertEquals(1, result.getUniversities().size()),
                () -> assertEquals("University of Malaya", result.getUniversities().get(0).getName())
        );
    }

    @Test
    void searchUniversities_withNoApiResults_returnsNotFoundMessage() {
        UniversityLookupService service = new UniversityLookupService(new StubUniversityApiClient(List.of()));

        UniversityLookupResult result = service.searchUniversities("Malaysia");

        assertAll(
                () -> assertFalse(result.isSuccess()),
                () -> assertEquals("No university records were found for the selected country.", result.getMessage()),
                () -> assertTrue(result.getUniversities().isEmpty())
        );
    }

    @Test
    void searchUniversities_whenApiThrowsIOException_returnsFallbackMessage() {
        UniversityLookupService service = new UniversityLookupService(new IOExceptionApiClient());

        UniversityLookupResult result = service.searchUniversities("Malaysia");

        assertAll(
                () -> assertFalse(result.isSuccess()),
                () -> assertEquals("University data is temporarily unavailable. Please try again later.", result.getMessage()),
                () -> assertTrue(result.getUniversities().isEmpty())
        );
    }

    @Test
    void searchUniversities_whenApiThrowsInterruptedException_returnsInterruptedMessage() {
        UniversityLookupService service = new UniversityLookupService(new InterruptedApiClient());

        UniversityLookupResult result = service.searchUniversities("Malaysia");

        assertAll(
                () -> assertFalse(result.isSuccess()),
                () -> assertEquals("University search was interrupted. Please try again.", result.getMessage()),
                () -> assertTrue(Thread.currentThread().isInterrupted())
        );

        Thread.interrupted();
    }

    private University createUniversity() {
        return new Gson().fromJson("""
                {
                  "name": "University of Malaya",
                  "country": "Malaysia",
                  "alpha_two_code": "MY",
                  "web_pages": ["https://um.edu.my"]
                }
                """, University.class);
    }

    private static class StubUniversityApiClient extends UniversityApiClient {
        private final List<University> universities;

        private StubUniversityApiClient(List<University> universities) {
            this.universities = universities;
        }

        @Override
        public List<University> fetchUniversitiesByCountry(String country) {
            return universities;
        }
    }

    private static class IOExceptionApiClient extends UniversityApiClient {
        @Override
        public List<University> fetchUniversitiesByCountry(String country) throws IOException {
            throw new IOException("API unavailable");
        }
    }

    private static class InterruptedApiClient extends UniversityApiClient {
        @Override
        public List<University> fetchUniversitiesByCountry(String country) throws InterruptedException {
            throw new InterruptedException("Interrupted");
        }
    }
}
