package com.bcnc.ecommerce.pricing.infraestructure.adapters.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRICES", indexes = {
        @Index(name = "IDX_BRAND_PRODUCT_PRICE", columnList = "BRAND_ID, PRODUCT_ID, PRICE_LIST, START_DATE, END_DATE")})
public class PriceEntity {

    @Id
    @Column(name = "PRICE_LIST")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceList;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private BrandEntity brandEntity;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity productEntity;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURR")
    private String currency;

}
