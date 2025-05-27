package com.producto.controller;

import com.producto.model.Product;
import com.producto.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productoService;

    public ProductController(final ProductService productService) {
        this.productoService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product producto) {
        return ResponseEntity.ok(productoService.createProduct(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findByDescription(@RequestParam String description) {
        return ResponseEntity.ok(productoService.findByDescription(description));
    }
}