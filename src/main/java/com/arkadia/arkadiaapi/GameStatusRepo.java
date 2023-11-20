package com.arkadia.arkadiaapi;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GameStatusRepo extends MongoRepository<GameStatus, Integer> {
    @Query(value="{'id': $0}")
    GameStatus findById(String id);

    @Query(value="{'id': $0}", delete = true)
    GameStatus deleteById(String id);
}
