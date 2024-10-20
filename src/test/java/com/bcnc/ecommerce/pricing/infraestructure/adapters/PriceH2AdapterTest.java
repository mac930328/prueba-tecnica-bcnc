package com.bcnc.ecommerce.pricing.infraestructure.adapters;

import com.bcnc.ecommerce.pricing.domain.models.Price;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.PriceEntity;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.mappers.PriceEntityMapper;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.repository.PriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceH2AdapterTest {

    @Mock
    private PriceJpaRepository priceJpaRepository;

    @Mock
    private PriceEntityMapper priceDboMapper;

    @InjectMocks
    private PriceH2Adapter priceH2Adapter;

    @Test
    void testGetPriceByDate() {

        LocalDateTime date = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;
        PriceEntity priceEntity = PriceEntity.builder().priceList(54L).build();
        Price price = Price.builder().priceList(54L).build();

        when(priceJpaRepository.findFirstPriceByDateAndProductAndBrand(any(), any(), any()))
                .thenReturn(Optional.of(priceEntity));
        when(priceDboMapper.priceEntitytoPrice(priceEntity)).thenReturn(price);

        Optional<Price> result = priceH2Adapter.getPriceByDate(date, productId, brandId);

        assertEquals(Optional.of(price), result);
    }

}
