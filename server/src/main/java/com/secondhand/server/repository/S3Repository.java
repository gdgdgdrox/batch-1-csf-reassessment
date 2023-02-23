package com.secondhand.server.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class S3Repository {
    
    @Autowired
    private AmazonS3 s3Client;
    

    public String storeImageInS3(MultipartFile image, String postingId){
        ObjectMetadata objMetaData = new ObjectMetadata();
        objMetaData.setContentType(image.getContentType());
        objMetaData.setContentLength(image.getSize());
        String imageUrl = "";
        try {
            PutObjectRequest putObjReq = new PutObjectRequest("gd-bucket-top-secret", postingId, image.getInputStream(), objMetaData);
            putObjReq.withCannedAcl(CannedAccessControlList.PublicRead);
            System.out.println("PUTTING IMAGE INTO S3");
            s3Client.putObject(putObjReq);
            imageUrl = "https://gd-bucket-top-secret.sgp1.digitaloceanspaces.com/%s".formatted(postingId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
