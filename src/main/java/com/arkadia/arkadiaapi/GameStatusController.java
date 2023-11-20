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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class GameStatusController {
    @Autowired
    private GameStatusRepo repo;

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
                GameStatus game = repo.findById(id);
                if (!game.equals(null)) return new ResponseEntity("ID does not exist", HttpStatus.BAD_REQUEST);
                game.setIsWorking(game.getIsWorking());
                repo.save(game);
                return new ResponseEntity("ID updated", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/GetAllGameStatuses")
    public ResponseEntity<List<GameStatus>> GetAllGameStatuses(){
        try {
            List<GameStatus> all = repo.findAll();
            return new ResponseEntity(all, HttpStatus.OK);
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
                repo.insert(gameStatus);
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
                repo.save(gameStatus);
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
                GameStatus game = repo.findById(id);
                if (!game.equals(null)) return new ResponseEntity("ID does not exist", HttpStatus.BAD_REQUEST);
                repo.deleteById(id);
                return new ResponseEntity("Game deleted", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
