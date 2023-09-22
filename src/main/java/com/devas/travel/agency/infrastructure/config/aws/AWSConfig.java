package com.devas.travel.agency.infrastructure.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AWSConfig {

    @Value("${aws.s3.access.key}")
    private String accessKey;

    @Value("${aws.s3.secret.key}")
    private String secretKey;

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(
                "AKIAXMA3DQ3WOLYIF4XQ",
                "bCgvDwZaWcq/+5itzfpy2JcFQ8Peh/rFSdH7V+MP");

    }

    @Bean
    public AmazonS3 amazonS3() {
       // system property (aws.region).,
// environment variable (AWS_REGION)
        System.setProperty("aws.region", "us-east-2");

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}
