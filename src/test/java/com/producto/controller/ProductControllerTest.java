package com.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto.dto.ProductRequest;
import com.producto.exception.GlobalExceptionHandler;
import com.producto.exception.ProductNotFoundException;
import com.producto.model.Product;
import com.producto.service.ProductService;
import org.junit.jupiter.api.AfterEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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

        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Product product = new Product(1L, "Laptop", 10, new BigDecimal("899.00"));

        when(productService.getById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Laptop"));

        verify(productService).getById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productService.getById(99L))
                .thenThrow(new ProductNotFoundException("Producto no encontrado con ID: 99"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());

        verify(productService).getById(99L);
    }


    @Test
    void shouldReturnProductsByDescription() throws Exception {
        Product product = new Product(1L, "Smartphone", 20, new BigDecimal("499.00"));

        when(productService.findByDescription("smart")).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products/search?description=smart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Smartphone"));

        verify(productService).findByDescription("smart");
    }
}
