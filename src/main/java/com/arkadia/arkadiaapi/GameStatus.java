package com.arkadia.arkadiaapi;

public class GameStatus {
    private String id;
    private String fullTitle;
    private boolean isWorking;
    private String[] searchTerms;

    GameStatus(String id, String fullTitle, boolean isWorking, String[] searchTerms){
        this.id = id;
        this.fullTitle = fullTitle;
        this.isWorking = isWorking;
        this.searchTerms = searchTerms;
    }

    public String getId(){
        return id;
    }

    public String getFullTitle() { return fullTitle; }

    public boolean getIsWorking() {
        return isWorking;
    }

    public String[] getSearchTerms() { return searchTerms; }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullTitle(String fullTitle) { this.fullTitle = fullTitle; }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public void setSearchTerms(String[] searchTerms) { this.searchTerms = searchTerms; }
}
