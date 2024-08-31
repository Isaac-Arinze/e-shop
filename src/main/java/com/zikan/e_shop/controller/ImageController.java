package com.zikan.e_shop.controller;

import com.zikan.e_shop.dto.ImageDto;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Image;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final ImageService imageService;
    @PostMapping
    public ResponseEntity<APIResponse> saveImages(List<MultipartFile> files, Long productId) {

        try {

            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new APIResponse("file uploaded successfully", imageDtos));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage (Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() +"\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<APIResponse> updateImage (@PathVariable Long imageId, @RequestBody MultipartFile file){

       try{

        Image image = imageService.getImageById(imageId);
        if(image != null) {
            imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new APIResponse("Update successful", null));
        }
        }catch (ResourceNotFoundExcepion e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
       }

       return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<APIResponse> deleteImage (@PathVariable Long imageId) {

        try {

            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("Image successfully deleted", null));
            }
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Image deleted failed", INTERNAL_SERVER_ERROR));

    }
}
