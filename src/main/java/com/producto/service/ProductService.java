package com.producto.service;

import com.producto.exception.ProductNotFoundException;
import com.producto.model.Product;
import com.producto.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product producto) {
        logger.info("Creando producto: {}", producto);
        return productRepository.save(producto);
    }

    public Product getById(Long id) {
        logger.info("Buscando producto por ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Producto con ID {} no encontrado", id);
                    return new ProductNotFoundException("Producto no encontrado con ID: " + id);
                });
    }

    public List<Product> findByDescription(String descripcion) {
        logger.info("Buscando producto por descripcion: {}", descripcion);
        List<Product> productos = productRepository.findByDescriptionContainingIgnoreCase(descripcion);

        if (productos.isEmpty()) {
            logger.error("No se encontraron productos con la descripción: {}", descripcion);
            throw new ProductNotFoundException("No se encontraron productos con la descripción: " + descripcion);
        }

        return productos;
    }

}