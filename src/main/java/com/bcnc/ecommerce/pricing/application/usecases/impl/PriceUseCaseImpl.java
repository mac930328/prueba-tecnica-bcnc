package com.bcnc.ecommerce.pricing.application.usecases.impl;

import com.bcnc.ecommerce.pricing.application.dtos.PriceDto;
import com.bcnc.ecommerce.pricing.application.mappers.PriceDtoMapper;
import com.bcnc.ecommerce.pricing.application.usecases.PriceUseCase;
import com.bcnc.ecommerce.pricing.domain.exceptions.NotFoundObjectException;
import com.bcnc.ecommerce.pricing.domain.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PriceUseCaseImpl implements PriceUseCase {

    public static final String NOT_FOUND_ERROR_MESSAGE = "There is no record";

    private final PriceRepository priceRepository;
    private final PriceDtoMapper priceDtoMapper;

    @Override
    public PriceDto getPriceByDate(LocalDateTime date, Long productId, Long brandId) {
        return priceRepository.getPriceByDate(date, productId, brandId)
                .map(priceDtoMapper::priceToPriceDto)
                .orElseThrow(() -> new NotFoundObjectException(NOT_FOUND_ERROR_MESSAGE));
    }

}
