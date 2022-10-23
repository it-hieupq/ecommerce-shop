package com.capstone.client.product.domain.repository;

import com.capstone.client.product.domain.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ImageRepo extends JpaRepository<Image, Integer> {
    Set<Image> findByImageIdIn(List<Integer> ids);
    Set<Image> findAllByImageIdIn(List<Integer> ids);
}