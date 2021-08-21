package com.arkadia.arkadiaapi;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameStatusController {
    @GetMapping("/UpdateGameStatus")
    public ResponseEntity<String> UpdateGameStatus(@RequestParam(value = "id", defaultValue = "IDNOTFOUND") String id){
        if(id.equals("IDNOTFOUND")) return new ResponseEntity(id, HttpStatus.BAD_REQUEST);
        else{
            try {
                Path path = Paths.get("games.json").toAbsolutePath();
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
}
