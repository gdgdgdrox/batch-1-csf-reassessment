package com.secondhand.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondhand.server.Utils;
import com.secondhand.server.service.ItemService;

import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api")
public class ItemRestController {

    @Autowired
    private ItemService itemSvc;

    @Autowired
    private ObjectMapper objMapper;

    
    @PostMapping(path="/posting", 
                consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
                produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveItem(@RequestPart MultipartFile image, @RequestPart("item") String data){
        System.out.println("IN POSTING CONTROLLER");
        // System.out.println("IMAGE: " + image);
        // System.out.println("ITEM : " + data);
        JsonObject savedItem = itemSvc.saveItem(image, data);
        return ResponseEntity.status(201).body(savedItem.toString());
    }

    @PutMapping(path="/posting/{id}")
    public ResponseEntity<String> confirmListing(@PathVariable("id") String postingId){
        System.out.println("IN PUT CONTROLLER");
        System.out.println("POSTING ID %s".formatted(postingId));
        boolean saved = itemSvc.saveConfirmedListing(postingId);
        if (!saved){
            String response = Utils.createResponse("message", "Posting ID %s not found".formatted(postingId)).toString();
            return ResponseEntity.status(404).body(response);
        }
        String response = Utils.createResponse("success", "Thank you for using our service.").toString();
        return ResponseEntity.ok(response);

    }
}
