package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;
import com.aleprimo.nova_store.entityServices.ProductService;
import com.aleprimo.nova_store.controller.mappers.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductRequestDTO buildRequestDTO() {
        return ProductRequestDTO.builder()
                .name("Producto test")
                .description("Desc test")
                .shortDescription("Short desc")
                .price(BigDecimal.valueOf(50))
                .stock(10)
                .imageUrl("http://img.com/p.jpg")
                .sku("SKU123")
                .isActive(true)
                .categoryId(1L)
                .build();
    }

    private ProductResponseDTO buildResponseDTO() {
        return ProductResponseDTO.builder()
                .id(1L)
                .name("Producto test")
                .description("Desc test")
                .shortDescription("Short desc")
                .price(BigDecimal.valueOf(50))
                .stock(10)
                .imageUrl("http://img.com/p.jpg")
                .sku("SKU123")
                .isActive(true)
                .categoryId(1L)
                .build();
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductRequestDTO req = buildRequestDTO();
        ProductResponseDTO res = buildResponseDTO();

        Mockito.when(productService.createProduct(any())).thenReturn(res);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(res.getId()));
    }

    @Test
    void testGetAll() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(res));
        Mockito.when(productService.getAllProducts(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(res.getId()));
    }

    @Test
    void testGetActive() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(res));
        Mockito.when(productService.getActiveProducts(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/products/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(res.getId()));
    }

    @Test
    void testSearchByName() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(res));
        Mockito.when(productService.searchByName(eq("Producto"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/products/search/name")
                        .param("name", "Producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(res.getName()));
    }

    @Test
    void testGetByCategory() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(res));
        Mockito.when(productService.getProductsByCategory(eq(1L), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoryId").value(1));
    }

    @Test
    void testSearchByCategoryAndName() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(res));
        Mockito.when(productService.searchByCategoryAndName(eq(1L), eq("test"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/products/search")
                        .param("categoryId", "1")
                        .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Producto test"));
    }

    @Test
    void testGetBySku_Found() throws Exception {
        ProductResponseDTO res = buildResponseDTO();
        Mockito.when(productService.getBySku("SKU123")).thenReturn(Optional.of(res));

        mockMvc.perform(get("/api/products/sku/SKU123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    void testGetBySku_NotFound() throws Exception {
        Mockito.when(productService.getBySku("NON_EXISTENT")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/sku/NON_EXISTENT"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductRequestDTO req = buildRequestDTO();
        ProductResponseDTO res = buildResponseDTO();
        Mockito.when(productService.updateProduct(eq(1L), any())).thenReturn(res);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
