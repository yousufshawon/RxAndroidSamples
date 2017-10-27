package com.yousuf.shawon.rxandroidsamples.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

// Created by user on 8/19/2016.
/**
 *
 */
public class MoviesResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override public String toString() {
        return "MoviesResponse{"
            + "page="
            + page
            + ", totalResults="
            + totalResults
            + ", totalPages="
            + totalPages
            + '}';
    }
}
