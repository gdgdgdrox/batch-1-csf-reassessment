package com.secondhand.server.service;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secondhand.server.Utils;

import com.secondhand.server.repository.RedisRepository;
import com.secondhand.server.repository.S3Repository;
import com.secondhand.server.repository.SQLRepository;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class ItemService {
    
    @Autowired
    private RedisRepository redisRepo;
    
    @Autowired
    private S3Repository s3Repo;

    @Autowired
    private SQLRepository sqlRepo;
    
    public JsonObject saveItem(MultipartFile image, String data){

        String postingId =  UUID.randomUUID().toString().substring(0,8);
        String imageUrl = s3Repo.storeImageInS3(image, postingId);
        System.out.println(imageUrl);

        JsonObject jo = Utils.stringToJsonObject(data);
        JsonObject item = Json.createObjectBuilder(jo)
                                    .add("postingId", postingId)
                                    .add("postingDate", new Date(System.currentTimeMillis()).getTime())
                                    .add("image", imageUrl)
                                    .build();

        redisRepo.saveItem(item);
        return item;
    }

    public boolean saveConfirmedListing(String postId){
        Optional<String> optItem = redisRepo.getItemById(postId);
        if (optItem.isEmpty()) {
            System.out.println("CANNOT FIND IN REDIS %s".formatted(postId));
            return false;
        }
        String itemString = optItem.get();
        JsonObject item = Utils.stringToJsonObject(itemString);
        System.out.println("ITEM > " + item.toString());

        //save to SQL
        sqlRepo.saveConfirmedListing(item);
        return true;
    }

    
}
