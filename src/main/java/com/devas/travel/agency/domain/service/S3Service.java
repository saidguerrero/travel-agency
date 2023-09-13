package com.devas.travel.agency.domain.service;

public interface S3Service {

    String  uploadObject(byte[] bytes, String fileName, int orderId);


    byte[] getFile(String filePath);

}
