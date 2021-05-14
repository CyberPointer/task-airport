package com.aviation.task.airport.service;

import com.aviation.task.airport.bootstrap.DataInit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CargoEntityServiceImplTest {
    @Autowired
    FlightEntityService flightEntityService;
    @Autowired
    CargoEntityService cargoEntityService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");

    private final String FILE_FLIGHT_JSON = "src/test/resources/json/testflight.json";
    private final String FILE_CARGO_JSON = "src/test/resources/json/testcargo.json";

    @BeforeEach
    void init() {
        DataInit dataLoader = new DataInit(flightEntityService, cargoEntityService, FILE_FLIGHT_JSON, FILE_CARGO_JSON);
        dataLoader.loadData();
    }

    @Test
    void save() {
        assertEquals(3, cargoEntityService.findAllCargo().size());

        cargoEntityService.findAllCargo().remove(2);

        assertEquals(2, cargoEntityService.findAllCargo().size());
    }

    @Test
    void findAllCargo() {
        assertEquals(3, cargoEntityService.findAllCargo().size());
    }

    @Test
    void calculateCargoWeight() {
        Long expectedWeight = 735l;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2018-08-30T08:05:36 -02:00", formatter);
        Integer flightNumber = 8074;

        assertEquals(expectedWeight, cargoEntityService.calculateCargoWeight(flightEntityService.findIdByFlightNumberAndDate(flightNumber, zonedDateTime)));
    }

    @Test
    void calculateBaggageWeight() {
        Long expectedWeight = 2690L;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2018-08-30T08:05:36 -02:00", formatter);
        Integer flightNumber = 8074;

        assertEquals(expectedWeight, cargoEntityService.calculateBaggageWeight(flightEntityService.findIdByFlightNumberAndDate(flightNumber, zonedDateTime)));
    }

    @Test
    void calculateTotalWeight() {
        Long expectedWeight = 3425L;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2018-08-30T08:05:36 -02:00", formatter);
        Integer flightNumber = 8074;

        assertEquals(expectedWeight, cargoEntityService.calculateTotalWeight(flightEntityService.findIdByFlightNumberAndDate(flightNumber, zonedDateTime)).getTotalWeight());
    }
}