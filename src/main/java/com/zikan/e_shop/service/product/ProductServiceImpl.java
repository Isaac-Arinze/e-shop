package com.zikan.e_shop.service.product;

import com.zikan.e_shop.dto.ImageDto;
import com.zikan.e_shop.dto.ProductDto;
import com.zikan.e_shop.exception.AlreadyExistsExcption;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Category;
import com.zikan.e_shop.model.Image;
import com.zikan.e_shop.model.Product;
import com.zikan.e_shop.repository.CategoryRepository;
import com.zikan.e_shop.repository.ImageRepository;
import com.zikan.e_shop.repository.ProductRepository;
import com.zikan.e_shop.request.AddProductRequest;
import com.zikan.e_shop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request) {

        if (productExists(request.getName(), request.getBrand()))
        {
            throw new AlreadyExistsExcption(request.getBrand() + " "+request.getName() + " Already Exists, kindly update products");
        }
        //check if d category is found hin d db
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
//create a helper mtd to only update quantity of similar product added to cart
    private boolean productExists(String name, String brand){
        return productRepository.existsByNameAndBrand(name, brand);
    }


//Helper method
    private Product createProduct(AddProductRequest request, Category category){

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );

    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepion("Product NOt Found"));

    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository :: delete,
                ()-> {throw new ResourceNotFoundExcepion("Product not found!");});

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundExcepion("Product Not Found"));



    }

    // Helper method for update product
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
        }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {

        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {

        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    //converting d image class to ImageDto associated with the productId
    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;

    }


}
