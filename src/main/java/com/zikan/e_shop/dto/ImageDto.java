package com.zikan.e_shop.dto;

import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String fileName;
    private String imageType;
    private String downloadUrl;
}
