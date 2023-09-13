package com.devas.travel.agency.infrastructure.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AWSConfig {

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(
                "AKIAXMA3DQ3WES55SSWJ",
                "laRs4tYoSHBgEkPBFnDbfB8Q0UPCw1dfYWWyshPe");

    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.US_EAST_1)
                .build();

    }
}
