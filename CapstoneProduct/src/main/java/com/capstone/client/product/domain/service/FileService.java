package com.capstone.client.product.domain.service;

import com.capstone.client.product.domain.model.dto.response.ImageDTO;
import com.capstone.client.product.domain.model.entity.Image;
import com.capstone.client.product.domain.repository.FileRepo;
import com.capstone.client.product.domain.repository.ImageRepo;
import com.capstone.client.product.domain.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    FileRepo fileRepo;

    @Autowired
    ImageRepo imageRepo;

    @Autowired
    ProductRepo productRepo;

    public List<String> deleteImages(List<Integer> ids) throws IOException {
        List<String> imagesDeleted = new ArrayList<>();
        Set<Image> images = imageRepo.findByImageIdIn(ids);
        List<Integer> idList = new ArrayList<>();


        if(images!=null && !images.isEmpty() ){
            for(Image image: images){
                idList.add(image.getImageId());
                imagesDeleted.add(image.getName());
                image.getProducts().forEach( p -> p.removeImage(image));

            }
        }

        imageRepo.deleteAllByIdInBatch(idList);

        for(String fileName: imagesDeleted){
            Files.deleteIfExists(Paths.get("uploads/images/"+fileName).toAbsolutePath());
        }

        return imagesDeleted;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepo.findAll();
        return toDTOList(images);
    }

    public List<ImageDTO> toDTOList(List<Image> images){
        if(images == null) return Collections.emptyList();
        return images.stream().map(Image::toDTO).collect(Collectors.toList());
    }

    public boolean isExisted(Integer imageId) {
        return imageRepo.existsById(imageId);
    }

    public ImageDTO add(Image image) {
        return imageRepo.save(image).toDTO();
    }

    public void saveFile(MultipartFile file) {
        fileRepo.save(file);
    }

    public Resource loadImage(String filename) {
        return fileRepo.loadImage(filename);
    }

    public Set<Image> findAllById(List<Integer> ids) {
        return imageRepo.findAllByImageIdIn(ids);
    }
}
