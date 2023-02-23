package com.secondhand.server.repository;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class RedisRepository {
    
    @Autowired
    @Qualifier("redisTemp")
    private RedisTemplate<String,String> redisTemp;

    public void saveItem(JsonObject item){
        String postingId = item.getString("postingId");
        System.out.println("Saving to Redis %s".formatted(postingId));
        //TTL 15min
        redisTemp.opsForValue().set(postingId, item.toString(), Duration.ofMinutes(15));

        System.out.println("Retrieving from Redis %s".formatted(postingId));
        String itemString = redisTemp.opsForValue().get(postingId);
        System.out.println(itemString);

    }

    // public String getItemById(String postingId){
    //     return redisTemp.opsForValue().get(postingId);
    // }

    public Optional<String> getItemById(String postingId){
        return Optional.ofNullable(redisTemp.opsForValue().getAndDelete(postingId));
    }



}
