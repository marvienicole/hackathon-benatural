package net.javaguides.springboot.benatural.controller;

import net.javaguides.springboot.benatural.model.Product;
import net.javaguides.springboot.benatural.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
// @RequestMapping("/api/products") No se si es requerido

public class ProductController {
    
    private ProductRepository productRepository;
    
    @Autowired

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/product/all")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(
            this.productRepository.findAll()
        );
    }

    @PostMapping("/product/save")
    public Product saveProduct(@RequestBody Product product){
        return this.productRepository.save(product);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id){
        Product product = this.productRepository.findById(id).orElseThrow(null);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("product/{id}")
    public Product updateUser(@RequestBody Product newProduct, @PathVariable(value = "id") Long id){
        return this.productRepository.findById(id)
            .map(product -> {
                product.setProductName (newProduct.getProductName());
                product.setProductDescription (newProduct.getProductDescription());
                product.setProductCategory (newProduct.getProductCategory());
                product.setProductPrice (newProduct.getProductPrice());
                return this.productRepository.save(product);

            })
            .orElseGet(()->{
                newProduct.setId(id);
                return this.productRepository.save(newProduct);
            });       
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id") Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(null);

        this.productRepository.delete(product);
        return ResponseEntity.ok().build();

    }
}
