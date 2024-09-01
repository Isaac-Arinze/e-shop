package com.zikan.e_shop.controller;


import com.zikan.e_shop.dto.ProductDto;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Product;
import com.zikan.e_shop.request.AddProductRequest;
import com.zikan.e_shop.request.ProductUpdateRequest;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProducts() {

        List<Product> products = productService.getAllProducts();
        //we do not need to expose entity class direclty to frontEnd

        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok(new APIResponse("Success", convertedProducts));
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId) {

        try {

            Product product = productService.getProductById(productId);
            var productDto = productService.convertToDto(product);

            return ResponseEntity.ok(new APIResponse("Success", productDto));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {

        try {
            Product theProduct = productService.addProduct(product);
            var productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new APIResponse("Product successfully added", productDto));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<APIResponse> updateProduct(ProductUpdateRequest request, @PathVariable Long productId) {
        try {

            Product product = productService.updateProduct(request, productId);
            var productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new APIResponse("Product successfully updated", productDto));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productid) {
        try {
            productService.deleteProductById(productid);
            return ResponseEntity.ok(new APIResponse("Product successfully updated", productid));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {

        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new APIResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new APIResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<APIResponse> findProductByBrand(@RequestParam String brand) {

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new APIResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<APIResponse> findProductByName(@PathVariable String name) {

        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new APIResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<APIResponse> findProductByCategory(@PathVariable String category) {

        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No products found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new APIResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {

        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);


            return ResponseEntity.ok(new APIResponse("product count", productCount));

        } catch (Exception e) {
            return ResponseEntity.ok(new APIResponse(e.getMessage(), null));
        }
    }


}
