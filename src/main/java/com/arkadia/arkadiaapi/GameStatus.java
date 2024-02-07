package com.arkadia.arkadiaapi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="cabinets")
public class GameStatus {
    @Id private String mongoId;
    private String id;
    private String fullTitle;
    private boolean isWorking;
    private String[] searchTerms;
    private String banner; // base 64 string encoding of banner image
    private String notes;
    private String[] parts;

    GameStatus(String id, String fullTitle, boolean isWorking, String[] searchTerms, String banner, String notes, String[] parts){
        this.id = id;
        this.fullTitle = fullTitle;
        this.isWorking = isWorking;
        this.searchTerms = searchTerms;
        this.banner = banner;
        this.notes = notes;
        this.parts = parts;
    }

    public String getId(){
        return id;
    }

    public String getFullTitle() { return fullTitle; }

    public boolean getIsWorking() {
        return isWorking;
    }

    public String[] getSearchTerms() { return searchTerms; }

    public String getBanner() { return banner; }

    public String getNotes() { return notes; }

    public String[] getParts() { return parts; }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullTitle(String fullTitle) { this.fullTitle = fullTitle; }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public void setSearchTerms(String[] searchTerms) { this.searchTerms = searchTerms; }

    public void setBanner(String banner) { this.banner = banner; }

    public void setNotes(String notes) { this.notes = notes; }

    public void setParts(String[] parts) { this.parts = parts;}
}
