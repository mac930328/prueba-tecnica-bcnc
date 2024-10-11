package com.bcnc.ecommerce.pricing.infraestructure.rest.controllers

import com.bcnc.ecommerce.pricing.infraestructure.rest.requests.PriceRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
class PriceControllerSpec extends Specification {

    MockMvc mockMvc

    @Autowired
    WebApplicationContext webApplicationContext;

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    def "Test Error #test: parametros invalidos: #message"() {
        given:
        def date = LocalDateTime.of(2020, 6, 14, 10, 0)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("applicationDate", "")
                .param("productId", "35455")
                .param("brandId", "2"))

        then:
        result.andExpectAll {
            status().is(httpStatus)
            jsonPath("message", message)
        }

        where:
        test | applicationDate     | productId | brandId | httpStatus              | message
        1    | null                | 35455     | 1       | status().isBadRequest() | PriceRequest.APPLICATION_DATE_NULL
        2    | LocalDateTime.now() | null      | 1       | status().isBadRequest() | PriceRequest.PRODUCT_ID_NULL
        3    | LocalDateTime.now() | 35455     | null    | status().isBadRequest() | PriceRequest.BRAND_ID_NULL
        4    | LocalDateTime.now() | 35455     | -1      | status().isBadRequest() | PriceRequest.BRAND_ID_POSITIVE
        5    | LocalDateTime.now() | -35455    | -1      | status().isBadRequest() | PriceRequest.PRODUCT_ID_POSITIVE

    }

    def "Test Error: No hay resultados para la busqueda"() {
        given:
        def date = LocalDateTime.of(2020, 6, 14, 10, 0)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("applicationDate", date.toString())
                .param("productId", "35455")
                .param("brandId", "2"))

        then:
        result.andExpect(status().isNotFound())

    }

    @Unroll
    def "Test #test: Consulta a las #hour:00 del día #day para el producto #productId y la marca #brandId"() {
        given:
        def date = LocalDateTime.of(year, month, day, hour, minute)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("applicationDate", date.toString())
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())).andReturn()

        then:
        assertPriceInfo(result, productId, brandId, priceList, startDate, endDate, expectedPrice)

        where:
        test | year | month | day | hour | minute | productId | brandId | priceList | startDate             | endDate               | expectedPrice
        1    | 2020 | 6     | 14  | 10   | 0      | 35455     | 1       | 1         | '2020-06-14T00:00:00' | '2020-12-31T23:59:59' | 35.50
        2    | 2020 | 6     | 14  | 16   | 0      | 35455     | 1       | 2         | '2020-06-14T15:00:00' | '2020-06-14T18:30:00' | 25.45
        3    | 2020 | 6     | 14  | 21   | 0      | 35455     | 1       | 1         | '2020-06-14T00:00:00' | '2020-12-31T23:59:59' | 35.50
        4    | 2020 | 6     | 15  | 10   | 0      | 35455     | 1       | 3         | '2020-06-15T00:00:00' | '2020-06-15T11:00:00' | 30.50
        5    | 2020 | 6     | 15  | 21   | 0      | 35455     | 1       | 4         | '2020-06-15T16:00:00' | '2020-12-31T23:59:59' | 38.95
    }

    def assertPriceInfo(result, productId, brandId, priceList, startDate, endDate, price) {
        status().isOk().match(result)
        jsonPath("productId").value(productId)
        jsonPath("brandId").value(brandId)
        jsonPath("priceList").value(priceList)
        jsonPath("startDate").value(startDate)
        jsonPath("endDate").value(endDate)
        jsonPath("price").value(price)
    }
}
