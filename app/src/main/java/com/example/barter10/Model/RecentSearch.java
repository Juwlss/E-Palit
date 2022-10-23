package com.example.barter10.Model;
public class RecentSearch {
    private String recent,recentkey;

    public RecentSearch(String recent, String recentkey) {
        this.recent = recent;
        this.recentkey = recentkey;
    }

    public RecentSearch() {
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    public String getRecentkey() {
        return recentkey;
    }

    public void setRecentkey(String recentkey) {
        this.recentkey = recentkey;
    }
}
