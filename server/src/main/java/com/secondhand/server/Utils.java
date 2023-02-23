package com.secondhand.server;

import java.io.StringReader;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {
    
    public static JsonObject stringToJsonObject(String data){
        StringReader sr = new StringReader(data);
        JsonReader jr = Json.createReader(sr);
        return jr.readObject();
    }

    public static JsonValue dateToJsonValue(){
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject jsonObject = factory.createObjectBuilder()
            .add("date", new Date().getTime())
            .build();

        JsonValue jsonValue = jsonObject;
        return jsonValue;
    }

    public static JsonObject createResponse(String key, String message){
        return Json.createObjectBuilder().add(key, message).build();
    }
}
