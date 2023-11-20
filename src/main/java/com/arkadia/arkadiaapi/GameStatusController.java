package com.arkadia.arkadiaapi;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class GameStatusController {
    @GetMapping("/SwitchGameStatus")
    public ResponseEntity<String> SwitchGameStatus(
        @RequestParam(value = "id", defaultValue = "IDNOTFOUND") String id,
        @RequestParam(value = "secret", defaultValue = "NOSECRET") String secret
    ){
        if(!AuthorizationChecker.checkSecret(secret)) return new ResponseEntity("Nice try buddy ;)", HttpStatus.UNAUTHORIZED);
        //what, you thought I'd just leave a sensitive secret lying around in a public github repo?
        else if(id.equals("IDNOTFOUND")) return new ResponseEntity(id, HttpStatus.BAD_REQUEST);
        else{
            try {
                Path path = Paths.get("games.json");
                String json = Files.readString(path, StandardCharsets.US_ASCII);
                Gson gson = new Gson();
                GameStatus[] games = gson.fromJson(json, GameStatus[].class);
                boolean found = false;
                for(int i = 0; i < games.length; i++){
                    if(games[i].getId().equals(id)){
                        games[i].setIsWorking(!games[i].getIsWorking());
                        found = true;
                        break;
                    }
                }

                if(!found) return new ResponseEntity("ID does not exist", HttpStatus.BAD_REQUEST);
                else {
                    String newJson = gson.toJson(games);
                    FileWriter writer = new FileWriter(path.toFile());
                    writer.write(newJson);
                    writer.close();
                    return new ResponseEntity("ID updated", HttpStatus.OK);
                }
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/GetAllGameStatuses")
    public ResponseEntity<List<GameStatus>> GetAllGameStatuses(){
        try {
            Path path = Paths.get("games.json");
            String json = Files.readString(path, StandardCharsets.US_ASCII);
            Gson gson = new Gson();
            GameStatus[] games = gson.fromJson(json, GameStatus[].class);
            return new ResponseEntity(Arrays.asList(games.clone()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(Arrays.asList((new GameStatus[0]).clone()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/GetFilteredGameStatuses")
    public ResponseEntity<List<GameStatus>> GetFilteredGameStatuses(
        @RequestParam(value = "searchParam") String searchParam
    ){
        try {
            Path path = Paths.get("games.json");
            String json = Files.readString(path, StandardCharsets.US_ASCII);
            Gson gson = new Gson();
            GameStatus[] games = gson.fromJson(json, GameStatus[].class);
            List<GameStatus> filteredList = Arrays.asList(games.clone());
            if(!searchParam.isBlank()) {
                for (int i = 0; i < filteredList.size(); i++) {
                    boolean fitsSearch = false;
                    GameStatus temp = filteredList.get(i);
                    for(int j = 0; j < temp.getSearchTerms().length; j++){
                        if(temp.getSearchTerms()[j].contains(searchParam)) fitsSearch = true;
                    }
                    if (!fitsSearch) {
                        filteredList.remove(i);
                        i--;
                    }
                }
            }
            return new ResponseEntity(filteredList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(Arrays.asList((new GameStatus[0]).clone()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/AddGameStatus")
    public ResponseEntity<String> AddGameStatus(
        @RequestParam(value = "secret", defaultValue = "NOSECRET") String secret,
        @RequestBody() GameStatus gameStatus
    ) {
        if(!AuthorizationChecker.checkSecret(secret)) return new ResponseEntity("Nice try buddy ;)", HttpStatus.UNAUTHORIZED);
        else{
            try {
                Path path = Paths.get("games.json");
                String json = Files.readString(path, StandardCharsets.US_ASCII);
                Gson gson = new Gson();
                ArrayList<GameStatus> games = new ArrayList<GameStatus>(Arrays.asList(gson.fromJson(json, GameStatus[].class)));
                games.add(gameStatus);

                GameStatus[] gamesArr = new GameStatus[games.size()];
                gamesArr = games.toArray(gamesArr);
                String newJson = gson.toJson(gamesArr);
                FileWriter writer = new FileWriter(path.toFile());
                writer.write(newJson);
                writer.close();
                return new ResponseEntity("Game added", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping("/UpdateGameStatus")
    public ResponseEntity<String> UpdateGameStatus(
        @RequestParam(value = "secret", defaultValue = "NOSECRET") String secret,
        @RequestBody() GameStatus gameStatus
    ) {
        if(!AuthorizationChecker.checkSecret(secret)) return new ResponseEntity("Nice try buddy ;)", HttpStatus.UNAUTHORIZED);
        else{
            try {
                Path path = Paths.get("games.json");
                String json = Files.readString(path, StandardCharsets.US_ASCII);
                Gson gson = new Gson();
                GameStatus[] games = gson.fromJson(json, GameStatus[].class);
                boolean found = false;
                for (int i = 0; i < games.length; i++) {
                    if (games[i].getId().equals(gameStatus.getId())) {
                        found = true;
                        games[i] = gameStatus;
                        break;
                    }
                }
                if (!found) {
                    return new ResponseEntity("Game with id " + gameStatus.getId() + " not found", HttpStatus.BAD_REQUEST);
                }
                String newJson = gson.toJson(games);
                FileWriter writer = new FileWriter(path.toFile());
                writer.write(newJson);
                writer.close();
                return new ResponseEntity("Game updated", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping("/DeleteGameStatus")
    public ResponseEntity<String> DeleteGameStatus(
        @RequestParam(value = "id", defaultValue = "IDNOTFOUND") String id,
        @RequestParam(value = "secret", defaultValue = "NOSECRET") String secret
    ) {
        if(!AuthorizationChecker.checkSecret(secret)) return new ResponseEntity("Nice try buddy ;)", HttpStatus.UNAUTHORIZED);
        else if(id.equals("IDNOTFOUND")) return new ResponseEntity(id, HttpStatus.BAD_REQUEST);
        else{
            try {
                Path path = Paths.get("games.json");
                String json = Files.readString(path, StandardCharsets.US_ASCII);
                Gson gson = new Gson();
                GameStatus[] games = gson.fromJson(json, GameStatus[].class);
                GameStatus[] newGames = new GameStatus[games.length - 1];
                boolean found = false;
                for (int i = 0; i < games.length; i++) {
                    int setInd = i - (found ? 1 : 0);
                    if (games[i].getId().equals(id)) {
                        found = true;
                    } else if (setInd < newGames.length) {
                        newGames[setInd] = games[i];
                    }
                }
                if (!found) {
                    return new ResponseEntity("Game with id " + id + " not found", HttpStatus.BAD_REQUEST);
                }
                String newJson = gson.toJson(newGames);
                FileWriter writer = new FileWriter(path.toFile());
                writer.write(newJson);
                writer.close();
                return new ResponseEntity("Game deleted", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
