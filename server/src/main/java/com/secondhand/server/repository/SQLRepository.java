package com.secondhand.server.repository;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class SQLRepository {

    private static final String SQL_SAVE_LISTING = """
        INSERT INTO postings (posting_id, posting_date, name, email, phone, title, description, image)
        VALUES (?,?,?,?,?,?,?,?)
            """;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveConfirmedListing(JsonObject jo){
        return jdbcTemplate.update(SQL_SAVE_LISTING, jo.getString("postingId"), new Date((jo.getJsonNumber("postingDate").longValue())), 
                                            jo.getString("name"), jo.getString("email"), 
                                            jo.getString("phone"), jo.getString("title"),
                                            jo.getString("description"),jo.getString("image"));
        
    }
}
