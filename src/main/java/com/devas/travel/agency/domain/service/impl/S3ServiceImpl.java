package com.devas.travel.agency.domain.service.impl;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.devas.travel.agency.domain.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
// snippet-end:[s3.java2.s3_object_operations.import]

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;


    public AWSCredentials credentials() {
        return new BasicAWSCredentials(
                "AKIAXMA3DQ3WOLYIF4XQ",
                "bCgvDwZaWcq/+5itzfpy2JcFQ8Peh/rFSdH7V+MP");

    }

    private S3Client getClient() {
        return S3Client.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();

    }


//    @Override
//    public String uploadObject(byte[] bytes, String fileName, int orderId) {
//        log.info("Uploading file to S3 folder {}", orderId);
////        System.setProperty("aws.region", "us-east-1");
//        String objectName = "";
//
//        objectName = String.valueOf(orderId) + "/" + fileName;
//        Long contentLength = Long.valueOf(bytes.length);
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(contentLength);
//        var putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(bytes), metadata)
//                .withCannedAcl(CannedAccessControlList.PublicRead);
//
//        amazonS3Client.putObject(putObjectRequest);
//        log.info("File uploaded successfully to S3");
//
//        return objectName;
//
//    }

    // Places a new video into an Amazon S3 bucket.
    @Override
    public String uploadObject(byte[] bytes, String fileName, int orderId) {
        log.info("Uploading file to S3 folder {}", orderId);
        S3Client s3 = getClient();
        String objectName = "";
        try {
            System.setProperty("aws.region", "us-east-1");
            // Set the tags to apply to the object.
            objectName = String.valueOf(orderId) + "/" + fileName;
            var theTags = "name=" + fileName + "&description=Se guarda en carpeta" + orderId;
            var putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .tagging(theTags)
                    .build();

            s3.putObject(putOb, RequestBody.fromBytes(bytes));
            log.info("File uploaded successfully to S3");
        } catch (S3Exception e) {
            log.error("Ocurrio un error al subir a S3: {}", e.awsErrorDetails().errorMessage());
        }
        return objectName;

    }


//    @Override
//    public byte[] getFile(String filePath) {
//        log.info("get file from S3 {}", filePath);
//        System.setProperty("aws.region", "us-east-1");
//        try (S3Client s3Client = S3Client.builder()
//                .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .build()) {
//            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(filePath)
//                    .build();
//            ResponseBytes<GetObjectResponse> requestResponseBytes = s3Client.getObjectAsBytes(getObjectRequest);
//            log.info("File downloaded successfully from S3");
//            return requestResponseBytes.asByteArray();
//
//        }
//    }

    @Override
    public byte[] getFile(String filePath) {
        log.info("get file from S3 {}", filePath);
        byte[] bytes = null;
//        try {
//            S3Object s3object = amazonS3Client.getObject(bucketName, filePath);
////            S3ObjectInputStream inputStream = s3object.getObjectContent();
//            bytes = IOUtils.toByteArray(s3object.getObjectContent());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//
//        }
        return bytes;

    }
}
