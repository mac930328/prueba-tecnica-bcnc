package com.bcnc.ecommerce.pricing.application.mappers;

import com.bcnc.ecommerce.pricing.application.dtos.PriceDto;
import com.bcnc.ecommerce.pricing.domain.models.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PriceDtoMapperTest {

    private PriceDtoMapper priceDtoMapper;

    @BeforeEach
    void setUp() {
        this.priceDtoMapper = Mappers.getMapper(PriceDtoMapper.class);
    }

    @Test
    void priceDtoToPriceTest() {
        PriceDto priceDto = PriceDto.builder()
                .price(BigDecimal.valueOf(35.50))
                .build();

        Price priceRes = priceDtoMapper.priceDtoToPrice(priceDto);

        assertEquals(priceDto.getPrice(), priceRes.getPriceAmount());
    }
}
