package com.scholarshiptracker.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityTest {

    @Test
    void getPrimaryWebsite_withWebsite_returnsFirstWebsite() {
        University university = new Gson().fromJson("""
                {
                  "name": "University of Malaya",
                  "country": "Malaysia",
                  "alpha_two_code": "MY",
                  "web_pages": ["https://um.edu.my", "https://example.com"]
                }
                """, University.class);

        assertAll(
                () -> assertEquals("University of Malaya", university.getName()),
                () -> assertEquals("Malaysia", university.getCountry()),
                () -> assertEquals("MY", university.getCountryCode()),
                () -> assertEquals("https://um.edu.my", university.getPrimaryWebsite())
        );
    }

    @Test
    void getPrimaryWebsite_withoutWebsite_returnsFallbackMessage() {
        University university = new Gson().fromJson("""
                {
                  "name": "Test University",
                  "country": "Malaysia",
                  "alpha_two_code": "MY"
                }
                """, University.class);

        assertEquals("No website available", university.getPrimaryWebsite());
    }

    @Test
    void getWebPages_whenModifiedExternally_doesNotChangeInternalArray() {
        University university = new Gson().fromJson("""
                {
                  "name": "Test University",
                  "country": "Malaysia",
                  "web_pages": ["https://first.com"]
                }
                """, University.class);

        String[] webPages = university.getWebPages();
        webPages[0] = "changed";

        assertEquals("https://first.com", university.getPrimaryWebsite());
    }

    @Test
    void toString_withUniversityDetails_returnsReadableText() {
        University university = new Gson().fromJson("""
                {
                  "name": "University of Malaya",
                  "country": "Malaysia",
                  "web_pages": ["https://um.edu.my"]
                }
                """, University.class);

        assertEquals("University of Malaya (Malaysia) - https://um.edu.my", university.toString());
    }
}
