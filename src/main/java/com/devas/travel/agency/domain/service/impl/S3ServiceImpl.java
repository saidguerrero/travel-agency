package com.devas.travel.agency.domain.service.impl;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.devas.travel.agency.domain.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

//    private S3Client getClient() {
//        return S3Client.builder()
//                .credentialsProvider(new AWSStaticCredentialsProvider(credentials()))
//                .region(Region.US_EAST_1)
//                .build();
//
//    }

    @Override
    public String uploadObject(byte[] bytes, String fileName, int orderId) {
        log.info("Uploading file to S3 folder {}", orderId);
//        try (S3Client s3Client = S3Client.builder()
//                .region(Region.US_EAST_1)
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .build()) {
        try (S3Client s3Client = S3Client.builder().build()) {
            String key = String.valueOf(orderId) + "/" + fileName;
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.putObject(request, RequestBody.fromByteBuffer(ByteBuffer.wrap(bytes)));
            log.info("File uploaded successfully to S3");
            return key;

        }
    }


    @Override
    public byte[] getFile(String filePath) {
        log.info("get file from S3 {}", filePath);
        try (S3Client s3Client = S3Client.builder().build()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();
            ResponseBytes<GetObjectResponse> requestResponseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            log.info("File downloaded successfully from S3");
            return requestResponseBytes.asByteArray();

        }
    }
}
