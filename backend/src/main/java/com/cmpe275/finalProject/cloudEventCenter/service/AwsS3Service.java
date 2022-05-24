package com.cmpe275.finalProject.cloudEventCenter.service;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class AwsS3Service {
	
	private AmazonS3 amazonS3;
	
    @Autowired
    public void AwsS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
	
    public String generatePreSignedURL(
    		String filePath,
    		String bucketName,
    		HttpMethod httpMethod
    ) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new java.util.Date());
    	calendar.add(Calendar.DATE, 1);
    	return amazonS3.generatePresignedUrl(
    			bucketName, 
    			filePath, 
    			calendar.getTime(), 
    			httpMethod
    	).toString();
    };
	
	
	
};