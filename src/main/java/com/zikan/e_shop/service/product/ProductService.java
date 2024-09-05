package com.zikan.e_shop.service.product;

import com.zikan.e_shop.dto.ProductDto;
import com.zikan.e_shop.model.Product;
import com.zikan.e_shop.request.AddProductRequest;
import com.zikan.e_shop.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName (String name);
    List<Product> getProductsByBrandAndName (String brand, String name);
    Long countProductsByBrandAndName (String brand, String name);


    List<ProductDto> getConvertedProducts(List<Product> products);

    //converting d image class to ImageDto associated with the productId
    ProductDto convertToDto(Product product);
}
