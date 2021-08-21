package com.arkadia.arkadiaapi;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateGameStatus {
    @GetMapping("/UpdateGameStatus")
    public ResponseEntity UpdateGameStatus(@RequestParam(value = "id", defaultValue = "IDNOTFOUND") String id){
        if(id.equals("IDNOTFOUND")) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
