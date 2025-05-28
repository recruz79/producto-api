package com.producto.service;

import com.producto.model.Product;
import com.producto.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setDescription("Notebook");
        mockProduct.setQuantity(5);
        mockProduct.setPrice(new BigDecimal("1200.0"));
        mockProduct.setCreationDate(LocalDateTime.now());
        mockProduct.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void createProduct_shouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        Product created = productService.createProduct(mockProduct);

        assertNotNull(created);
        assertEquals("Notebook", created.getDescription());
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void getById_shouldReturnProductWhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Optional<Product> result = productService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Notebook", result.get().getDescription());
    }

    @Test
    void getById_shouldReturnEmptyWhenNotExists() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByDescription_shouldReturnListOfProducts() {
        List<Product> products = List.of(mockProduct);
        when(productRepository.findByDescriptionContainingIgnoreCase("lap")).thenReturn(products);

        List<Product> result = productService.findByDescription("lap");

        assertEquals(1, result.size());
        assertEquals("Notebook", result.get(0).getDescription());
    }
}
