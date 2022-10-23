package com.capstone.client.product.domain.model.entity;

import com.capstone.client.product.domain.common.Stringify;
import com.capstone.client.product.domain.model.dto.response.ImageDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;
    private String name;
    private String url;
    private String mediaType;
    private String uploadedDate;

    @ManyToMany(mappedBy = "images", fetch = FetchType.EAGER)
    List<Product> products = new ArrayList<>();

    public ImageDTO toDTO(){
        return ImageDTO.builder()
                .imageId(this.imageId)
                .name(this.name)
                .url(Stringify.SERVICE_URL + this.url)
                .uploadedDate(this.getUploadedDate())
                .mediaType(this.mediaType)
                .build();
    }

}
