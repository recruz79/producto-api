package com.producto.controller;

import com.producto.model.Product;
import com.producto.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product producto) {
        return ResponseEntity.ok(service.createProduct(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findByDescription(@RequestParam String description) {
        return ResponseEntity.ok(service.findByDescription(description));
    }
}