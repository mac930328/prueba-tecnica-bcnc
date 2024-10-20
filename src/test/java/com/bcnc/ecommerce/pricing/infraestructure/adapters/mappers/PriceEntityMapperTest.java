package com.bcnc.ecommerce.pricing.infraestructure.adapters.mappers;

import com.bcnc.ecommerce.pricing.domain.models.Brand;
import com.bcnc.ecommerce.pricing.domain.models.Price;
import com.bcnc.ecommerce.pricing.domain.models.Product;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.PriceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PriceEntityMapperTest {

    @Autowired
    private PriceEntityMapper priceEntityMapper;

    @Test
    void priceToPriceEntityTest() {

        Price price = Price.builder()
                .priceAmount(BigDecimal.valueOf(35.50))
                .product(Product.builder().build())
                .brand(Brand.builder().build())
                .build();

        PriceEntity priceEntity = priceEntityMapper.priceToPriceEntity(price);

        assertEquals(price.getPriceAmount(), priceEntity.getPrice());
    }
}
