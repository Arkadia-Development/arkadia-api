package com.arkadia.arkadiaapi;

public class GameStatus {
    private String id;
    private boolean isWorking;

    GameStatus(String id, boolean isWorking){
        this.id = id;
        this.isWorking = isWorking;
    }

    public String getId(){
        return id;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
}
