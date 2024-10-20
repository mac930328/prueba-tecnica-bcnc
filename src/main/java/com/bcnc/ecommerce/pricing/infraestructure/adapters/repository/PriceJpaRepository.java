package com.bcnc.ecommerce.pricing.infraestructure.adapters.repository;

import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.BrandEntity;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.PriceEntity;
import com.bcnc.ecommerce.pricing.infraestructure.adapters.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.productEntity = :product " +
            "AND p.brandEntity = :brand " +
            "AND p.startDate < :applicationDate " +
            "AND p.endDate > :applicationDate " +
            "ORDER BY p.priority DESC " +
            "LIMIT 1 ")
    Optional<PriceEntity> findFirstPriceByDateAndProductAndBrand(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("product") ProductEntity product,
            @Param("brand") BrandEntity brand);

}
