package com.capstone.client.product.domain.repository;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FileRepo {
    public void init();
    public void save(MultipartFile file);
    public Resource loadImage(String filename);
    public void deleteAll();
    public Stream<Path> loadAll();
}
