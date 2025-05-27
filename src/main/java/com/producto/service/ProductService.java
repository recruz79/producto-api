package com.producto.service;

import com.producto.model.Product;
import com.producto.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Product createProduct(Product producto) {
        logger.info("Creando producto: {}", producto);
        return productoRepository.save(producto);
    }

    public Optional<Product> getById(Long id) {
        logger.info("Buscando producto por ID: {}", id);
        return productoRepository.findById(id);
    }

    public List<Product> findByDescription(String descripcion) {
        logger.info("Buscando producto por descripcion: {}", descripcion);
        return productoRepository.findByDescriptionContainingIgnoreCase(descripcion);
    }
}