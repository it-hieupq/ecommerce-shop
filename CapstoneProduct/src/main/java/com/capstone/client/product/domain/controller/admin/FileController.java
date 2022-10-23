package com.capstone.client.product.domain.controller.admin;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.model.dto.response.ImageDTO;
import com.capstone.client.product.domain.model.entity.Image;
import com.capstone.client.product.domain.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@RestController("AdminFileController")
@RequestMapping("/admin/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping
    public ResponseEntity<Object> getAllImages() {

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        List<ImageDTO> imageDTOList = new ArrayList<>();
        String message = "Get all image";

        try{
            imageDTOList = fileService.getAllImages();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }
        map.put("images", imageDTOList);
        return ResponseDTO.getResponse(message, status, map);
    }

    @PostMapping("")
    public ResponseEntity<Object> uploadFiles(@RequestParam("files") MultipartFile[] files) {

        HttpStatus status = HttpStatus.OK;
        String message = "File uploaded successfully";
        Map<String, Object> map = new HashMap<>();
        List<ImageDTO> filesUploaded = new ArrayList<>();

        try {
            Arrays.asList(files).stream().forEach( file -> {
                System.out.println(file.getName());
                if(file.getContentType().toLowerCase().startsWith("image/")){
                    fileService.saveFile(file);

                    Image image = Image.builder()
                            .name(file.getOriginalFilename())
                            .url("/media/images/"+file.getOriginalFilename())
                            .uploadedDate(Instant.now().toString())
                            .mediaType(file.getContentType())
                            .build();

                    ImageDTO savedImage = fileService.add(image);
                    filesUploaded.add(savedImage);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        map.put("files", filesUploaded);
        map.put("total_uploaded", filesUploaded.size());
        return ResponseDTO.getResponse(message, status, map);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteImages(@RequestBody List<Integer> ids) {

        HttpStatus status = HttpStatus.OK;
        String message = "Deleted files";
        Map<String, Object> map = new HashMap<>();
        List<String> imagesDeleted  = new ArrayList<>();

        try {
            imagesDeleted = fileService.deleteImages(ids);
        } catch (Exception e) {
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        map.put("images", imagesDeleted);
        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.loadImage(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}

//            filesInfo = fileUploaderRepo.loadAll().map(path -> {
//                String filename = path.getFileName().toString();
//
//                String url = MvcUriComponentsBuilder
//                        .fromMethodName(FileUploaderController.class, "getFile", path.getFileName().toString()).build().toString();
//
//                return new FileInfo(filename, url);
//            }).collect(Collectors.toList());