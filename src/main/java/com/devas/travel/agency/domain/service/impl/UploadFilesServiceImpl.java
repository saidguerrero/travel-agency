package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.UploadFile;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.OrderFiles;
import com.devas.travel.agency.domain.service.S3Service;
import com.devas.travel.agency.domain.service.UploadFilesService;
import com.devas.travel.agency.infrastructure.adapter.repository.OrderFilesRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.OrdersRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFilesServiceImpl implements UploadFilesService {

    private final OrderFilesRepository orderFilesRepository;

    private final OrdersRepository orderRepository;

    private final S3Service s3Service;

    @Override
    public Either<Error, String> saveUploadFile(int orderId, List<UploadFile> uploadFiles) {
        try {
            uploadFiles.forEach(uploadFile -> {
                log.info("File: {}", uploadFile);
                OrderFiles orderFiles = new OrderFiles();
                orderFiles.setFileExtension(uploadFile.getFileExtension());
                byte[] file = null;
                var filePath = "";
                if (!uploadFile.getFile().isEmpty()) {
                    var base64String = uploadFile.getFile().get(0);
                    file = Base64.getDecoder().decode(base64String);
//                    filePath = writeUploadFile(file, uploadFile.getFileName(), orderId);
                    filePath = s3Service.uploadObject(file, uploadFile.getFileName(), orderId);
                }
                orderFiles.setFilePath(filePath);
                orderFiles.setFileName(uploadFile.getFileName());
                orderFiles.setFileExtension(uploadFile.getFileExtension());
                orderFiles.setTypeFileId(uploadFile.getFileTypeId());
                orderFiles.setCreateDate(LocalDateTime.now());
                orderFiles.setActive(Boolean.TRUE);
                orderRepository.findById((long) orderId).ifPresent(orderFiles::setOrdersByOrderId);
                orderFilesRepository.save(orderFiles);
            });
            return Either.right("Successfully");

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return Either.left(Error.builder().message(e.getMessage()).build());

        }
    }

    //write the file in a new folder
//save the path in the database
    public String writeUploadFile(byte[] bytes, String fileName, int orderId) {
        String filePath = "";
        try {
            String pathOfDir = "/Users/said.guerrero/Documents/BUMERAN/files/" + String.valueOf(orderId);
            File theDir = new File(pathOfDir);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            filePath = pathOfDir + "/" + fileName + ".pdf";
            FileUtils.writeByteArrayToFile(new File(filePath), bytes);
            return filePath;
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return "Error";
        }
    }

    @Override
    public byte[] getFileById(Long id) {
        var optional = orderFilesRepository.findById(id);
        if (optional.isPresent()) {
            try {
                var filePath = optional.get().getFilePath();
                if (filePath != null && !filePath.equalsIgnoreCase("Error")) {
                    //TODO: get from S3
//                    File file = new File(filePath);
//                    return FileUtils.readFileToByteArray(file);
                   return s3Service.getFile(filePath);
                }
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        return new byte[0];

    }
}
