package com.bcnc.ecommerce.pricing.infraestructure.adapters.mappers;

import com.bcnc.ecommerce.pricing.domain.models.Product;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductEntityMapperTest {

    private ProductEntityMapper productEntityMapper;

    @BeforeEach
    void setUp() {
        this.productEntityMapper = Mappers.getMapper(ProductEntityMapper.class);
    }

    @Test
    void productEntityToProductTest() {

        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .build();

        Product product = productEntityMapper.productEntityToProduct(productEntity);

        assertEquals(productEntity.getId(), product.getId());
    }
}
