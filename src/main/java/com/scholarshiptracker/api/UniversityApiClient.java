package com.scholarshiptracker.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Client for retrieving university data from an external REST API.
 */
public class UniversityApiClient {

    private static final String BASE_URL = "http://universities.hipolabs.com/search?country=";
    private static final int TIMEOUT_SECONDS = 8;

    private final HttpClient httpClient;
    private final Gson gson;

    public UniversityApiClient() {
        this(HttpClient.newHttpClient(), new Gson());
    }

    public UniversityApiClient(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    /**
     * Fetches universities by country name.
     *
     * @param country country name, for example Malaysia
     * @return list of universities returned by the API
     * @throws IOException if the API cannot be reached
     * @throws InterruptedException if the request is interrupted
     */
    public List<University> fetchUniversitiesByCountry(String country)
            throws IOException, InterruptedException {
        String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + encodedCountry))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("API request failed with status code " + response.statusCode());
        }

        University[] universities = gson.fromJson(response.body(), University[].class);
        return Arrays.asList(universities);
    }
}
