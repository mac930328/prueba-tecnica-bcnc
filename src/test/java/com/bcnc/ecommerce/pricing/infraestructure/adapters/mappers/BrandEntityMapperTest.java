package com.bcnc.ecommerce.pricing.infraestructure.adapters.mappers;

import com.bcnc.ecommerce.pricing.domain.models.Brand;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BrandEntityMapperTest {

    private BrandEntityMapper brandEntityMapper;

    @BeforeEach
    void setUp() {
        this.brandEntityMapper = Mappers.getMapper(BrandEntityMapper.class);
    }

    @Test
    void brandToBrandEntityTest() {

        Brand brand = Brand.builder()
                .id(1L)
                .build();

        BrandEntity brandEntity = brandEntityMapper.brandToBrandEntity(brand);

        assertEquals(brand.getId(), brandEntity.getId());
    }
}
