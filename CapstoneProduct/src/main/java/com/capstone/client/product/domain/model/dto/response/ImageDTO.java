package com.capstone.client.product.domain.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Integer imageId;
    private String name;
    private String url;
    private String uploadedDate;
    private String mediaType;
}
