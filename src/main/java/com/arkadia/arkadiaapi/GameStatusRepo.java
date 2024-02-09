package com.arkadia.arkadiaapi;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GameStatusRepo extends MongoRepository<GameStatus, Integer> {
    GameStatus findById(String id);

    GameStatus deleteById(String id);
}
