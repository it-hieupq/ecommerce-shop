package com.capstone.client.product.domain.controller.user;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.repository.FileRepo;
import com.capstone.client.product.domain.model.dto.response.ImageDTO;
import com.capstone.client.product.domain.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("UserFileController")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    FileRepo fileRepo;

    @GetMapping("/uploads")
    public ResponseEntity<Object> getAllFiles() {
        Map<String, Object> map = new HashMap<>();
        String message = "Get files successfully";
        HttpStatus status = HttpStatus.OK;

        List<ImageDTO> images = null;

        try {
            images = fileService.getAllImages();
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("images", images);

        return ResponseDTO.getResponse( message, status, map);
//==========
//        List<FileInfo> fileInfos = fileUploaderRepo.loadAll().map(path -> {
//            String filename = path.getFileName().toString();
//
//            System.out.println(filename);
//
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
//            return new FileInfo(filename, url);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);

    }

    @GetMapping({"/media/images/{filename:.+}", "/uploads/{filename:.+}","/images/{filename:.+}"})
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileRepo.loadImage(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(file);
    }

}
