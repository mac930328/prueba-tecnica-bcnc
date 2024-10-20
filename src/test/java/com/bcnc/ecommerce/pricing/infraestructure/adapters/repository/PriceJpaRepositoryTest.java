package com.bcnc.ecommerce.pricing.infraestructure.adapters.repository;

import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.BrandEntity;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.PriceEntity;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PriceJpaRepositoryTest {

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    @Test
    void test1FindFirstPriceByDateAndProductAndBrand() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 06, 14, 10, 00);
        ProductEntity productEntity = ProductEntity.builder().id(35455L).name("Product 1").build();
        BrandEntity brandEntity = BrandEntity.builder().id(1L).name("Zara").build();
        PriceEntity priceEntity = PriceEntity.builder()
                .priceList(1L)
                .productEntity(productEntity)
                .brandEntity(brandEntity)
                .startDate(LocalDateTime.of(2020, 06, 14, 00, 00))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .priority(0)
                .price(BigDecimal.valueOf(35.50).setScale(2))
                .currency("EUR")
                .build();

        Optional<PriceEntity> result = priceJpaRepository.findFirstPriceByDateAndProductAndBrand(
                applicationDate, productEntity, brandEntity);

        assertEquals(Optional.of(priceEntity), result);
    }

    @Test
    void test2FindFirstPriceByDateAndProductAndBrand() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 06, 14, 16, 00);
        ProductEntity productEntity = ProductEntity.builder().id(35455L).name("Product 1").build();
        BrandEntity brandEntity = BrandEntity.builder().id(1L).name("Zara").build();
        PriceEntity priceEntity = PriceEntity.builder()
                .priceList(2L)
                .productEntity(productEntity)
                .brandEntity(brandEntity)
                .startDate(LocalDateTime.of(2020, 06, 14, 15, 00))
                .endDate(LocalDateTime.of(2020, 06, 14, 18, 30))
                .priority(1)
                .price(BigDecimal.valueOf(25.45).setScale(2))
                .currency("EUR")
                .build();

        Optional<PriceEntity> result = priceJpaRepository.findFirstPriceByDateAndProductAndBrand(
                applicationDate, productEntity, brandEntity);

        assertEquals(Optional.of(priceEntity), result);
    }

}
