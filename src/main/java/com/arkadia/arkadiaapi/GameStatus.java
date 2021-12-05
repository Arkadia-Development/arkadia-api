package com.arkadia.arkadiaapi;

public class GameStatus {
    private String id;
    private boolean isWorking;
    private String[] searchTerms;

    GameStatus(String id, boolean isWorking, String[] searchTerms){
        this.id = id;
        this.isWorking = isWorking;
        this.searchTerms = searchTerms;
    }

    public String getId(){
        return id;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public String[] getSearchTerms() { return searchTerms; }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public void setSearchTerms(String[] searchTerms) { this.searchTerms = searchTerms; }
}
