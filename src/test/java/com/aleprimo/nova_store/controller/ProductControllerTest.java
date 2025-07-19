package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.product.ProductRequestDTO;

import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Long categoryId;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        Category category = new Category();
        category.setName("Electrónica");
        categoryId = categoryRepository.save(category).getId();
    }

    @Test
    void createProduct_shouldReturn201() throws Exception {
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Producto 1")
                .description("Descripción larga")
                .shortDescription("Corta")
                .price(BigDecimal.valueOf(19.99))
                .stock(50)
                .imageUrl("https://img.com/1.jpg")
                .sku("SKU123")
                .isActive(true)
                .categoryId(categoryId)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Producto 1"));
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk());
    }

    @Test
    void getActive_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products/active"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByName_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products/search/name")
                        .param("name", "Producto"))
                .andExpect(status().isOk());
    }

    @Test
    void getByCategory_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products/category/" + categoryId))
                .andExpect(status().isOk());
    }

    @Test
    void searchByCategoryAndName_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("categoryId", categoryId.toString())
                        .param("name", "Producto"))
                .andExpect(status().isOk());
    }

    @Test
    void getBySku_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/sku/NOSKU"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_shouldReturn404_whenNotExists() throws Exception {
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Nuevo Nombre")
                .description("Nueva desc")
                .shortDescription("Corta")
                .price(BigDecimal.valueOf(29.99))
                .stock(25)
                .imageUrl("https://img.com/2.jpg")
                .sku("SKUEDIT")
                .isActive(true)
                .categoryId(categoryId)
                .build();

        mockMvc.perform(put("/api/products/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_shouldReturn204_whenNotExists() throws Exception {
        mockMvc.perform(delete("/api/products/9999"))
                .andExpect(status().isNoContent());
    }
}
