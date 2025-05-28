package com.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto.dto.ProductRequest;
import com.producto.model.Product;
import com.producto.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        ProductRequest request = new ProductRequest("Phone", 5, new BigDecimal("299.99"));
        Product savedProduct = new Product(1L, "Phone", 5, new BigDecimal("299.99"));

        when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Phone"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.price").value(new BigDecimal("299.99")));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Product product = new Product(1L, "Laptop", 10, new BigDecimal("899.00"));

        when(productService.getById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Laptop"));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnProductsByDescription() throws Exception {
        Product product = new Product(1L, "Smartphone", 20, new BigDecimal("499.00"));

        when(productService.findByDescription("smart")).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products/search?description=smart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Smartphone"));
    }
}
