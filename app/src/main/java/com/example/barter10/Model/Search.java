package com.example.barter10.Model;

public class Search {
    String recentSearch;
    String recentSearchId;

    public Search() {
    }

    public Search(String recentSearch, String recentSearchId) {
        this.recentSearch = recentSearch;
        this.recentSearchId = recentSearchId;
    }

    public String getRecentSearch() {
        return recentSearch;
    }

    public void setRecentSearch(String recentSearch) {
        this.recentSearch = recentSearch;
    }

    public String getRecentSearchId() {
        return recentSearchId;
    }

    public void setRecentSearchId(String recentSearchId) {
        this.recentSearchId = recentSearchId;
    }
}
