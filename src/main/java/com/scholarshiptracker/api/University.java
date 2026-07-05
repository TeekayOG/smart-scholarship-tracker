package com.scholarshiptracker.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a university record returned by the external university API.
 */
public class University {

    private String name;
    private String country;

    @SerializedName("alpha_two_code")
    private String countryCode;

    @SerializedName("web_pages")
    private String[] webPages;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String[] getWebPages() {
        return webPages == null ? new String[0] : webPages.clone();
    }

    public String getPrimaryWebsite() {
        if (webPages == null || webPages.length == 0) {
            return "No website available";
        }
        return webPages[0];
    }

    @Override
    public String toString() {
        return name + " (" + country + ") - " + getPrimaryWebsite();
    }
}
