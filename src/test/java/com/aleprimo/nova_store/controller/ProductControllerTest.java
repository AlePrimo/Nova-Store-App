package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;
import com.aleprimo.nova_store.entityServices.ProductService;
import com.aleprimo.nova_store.controller.mappers.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductRequestDTO requestDTO;
    private ProductResponseDTO responseDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        requestDTO = ProductRequestDTO.builder()
                .name("Producto Test")
                .description("Descripción larga del producto")
                .shortDescription("Descripción corta")
                .price(new BigDecimal("1000.00"))
                .stock(10)
                .imageUrl("http://image.com/product.jpg")
                .sku("PROD-001")
                .isActive(true)
                .categoryId(1L)
                .build();

        responseDTO = ProductResponseDTO.builder()
                .id(1L)
                .name("Producto Test")
                .description("Descripción larga del producto")
                .shortDescription("Descripción corta")
                .price(new BigDecimal("1000.00"))
                .stock(10)
                .imageUrl("http://image.com/product.jpg")
                .sku("PROD-001")
                .isActive(true)
                .categoryName("Electrónica")
                .build();

        pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
    }

    @Test
    void createProduct_shouldReturnCreated() throws Exception {
        Mockito.when(productService.createProduct(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Producto Test"));
    }

    @Test
    void getAll_shouldReturnPagedProducts() throws Exception {
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(productService.getAllProducts(any())).thenReturn(page);

        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Producto Test"));
    }

    @Test
    void getActive_shouldReturnPagedActiveProducts() throws Exception {
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(productService.getActiveProducts(any())).thenReturn(page);

        mockMvc.perform(get("/api/products/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].isActive").value(true));
    }

    @Test
    void searchByName_shouldReturnFilteredProducts() throws Exception {
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(productService.searchByName(eq("Producto"), any())).thenReturn(page);

        mockMvc.perform(get("/api/products/search/name")
                        .param("name", "Producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Producto Test"));
    }

    @Test
    void getByCategory_shouldReturnFilteredByCategory() throws Exception {
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(productService.getProductsByCategory(eq(1L), any())).thenReturn(page);

        mockMvc.perform(get("/api/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoryName").value("Electrónica"));
    }

    @Test
    void searchByCategoryAndName_shouldReturnFilteredProducts() throws Exception {
        Page<ProductResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(productService.searchByCategoryAndName(eq(1L), eq("Producto"), any())).thenReturn(page);

        mockMvc.perform(get("/api/products/search")
                        .param("categoryId", "1")
                        .param("name", "Producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Producto Test"));
    }

    @Test
    void getBySku_shouldReturnProductIfExists() throws Exception {
        Mockito.when(productService.getBySku("PROD-001")).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/products/sku/PROD-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("PROD-001"));
    }

    @Test
    void getBySku_shouldReturnNotFoundIfNotExists() throws Exception {
        Mockito.when(productService.getBySku("NO-EXISTE")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/sku/NO-EXISTE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        Mockito.when(productService.updateProduct(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
