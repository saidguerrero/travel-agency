package com.devas.travel.agency.domain.service.impl;


import com.devas.travel.agency.domain.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
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


    @Override
    public String uploadObject(byte[] bytes, String fileName, int orderId) {
        try (S3Client s3Client = S3Client.builder().build()) {
            String key = String.valueOf(orderId) + "/" + fileName;
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.putObject(request, RequestBody.fromByteBuffer(ByteBuffer.wrap(bytes)));
            return key;

        }
    }


    @Override
    public byte[] getFile(String filePath) {
        try (S3Client s3Client = S3Client.builder().build()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();
            ResponseBytes<GetObjectResponse> requestResponseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return requestResponseBytes.asByteArray();

        }
    }
}
