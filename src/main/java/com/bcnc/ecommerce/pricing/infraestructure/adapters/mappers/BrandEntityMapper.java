package com.bcnc.ecommerce.pricing.infraestructure.adapters.mappers;

import com.bcnc.ecommerce.pricing.domain.models.Brand;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.BrandEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandEntityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Brand brandEntityToBrand(BrandEntity brandEntity);

    @InheritInverseConfiguration
    BrandEntity brandToBrandEntity(Brand brand);
}
