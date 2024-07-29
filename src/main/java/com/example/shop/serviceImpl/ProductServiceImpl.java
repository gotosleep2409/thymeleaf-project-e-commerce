package com.example.shop.serviceImpl;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Product;
import com.example.shop.respository.ProductRepository;
import com.example.shop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product","Id",id)) ;
    }
    @Override
    public Product updateProduct(Product product, long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product","Id",id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setDetail(product.getDetail());
        String image = product.getImage();
        String existingImage = existingProduct.getImage();
        if(image == null)
        {
            existingProduct.setImage(existingImage);
        }else {
            existingProduct.setImage(product.getImage());
        }
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPriceSale(product.getPriceSale());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);

    }
    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product","Id",id));
        productRepository.deleteById(id);
    }

}
