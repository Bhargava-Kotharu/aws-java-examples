package com.bkotharu.examples.s3.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class S3Client
{

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    private AmazonS3 amazonS3;


    @SuppressWarnings("deprecation")
    @PostConstruct
    private void initializeAmazon()
    {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = new AmazonS3Client(credentials);
    }


    public String uploadFileTos3bucket(String fileName, File file)
    {
        // PutObjectResult result =
        amazonS3.putObject(
            new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return endpointUrl + "/" + bucketName + "/" + fileName;

    }


    public List<String> getFilesListFromS3Bucket()
    {
        ListObjectsRequest listObjectsRequest =
            new ListObjectsRequest()
                .withBucketName(bucketName);
        ObjectListing objectListing;
        List<String> fileNames = new ArrayList<String>();

        do
        {
            objectListing = amazonS3.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries())
            {
                fileNames.add(objectSummary.getKey());
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        }
        while (objectListing.isTruncated());

        return fileNames;
    }


    public File getFileFromS3Bucket(String fileName) throws Exception
    {
        OutputStream writer = null;
        InputStream reader = null;
        File file = null;
        try
        {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));

            reader =
                new BufferedInputStream(
                    s3Object.getObjectContent());
            file = new File(fileName);
            writer = new BufferedOutputStream(new FileOutputStream(file));

            int read = -1;
            while ((read = reader.read()) != -1)
            {
                writer.write(read);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (writer != null)
            {
                writer.flush();
                writer.close();
            }
            if (reader != null)
                reader.close();
        }
        return file;

    }


    public Boolean deleteFileFromS3Bucket(String fileName)
    {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return true;
    }
}
