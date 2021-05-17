package com.aviation.task.airport.service;

import com.aviation.task.airport.bootstrap.DataInit;
import com.aviation.task.airport.constants.Constants;
import com.aviation.task.airport.model.CargoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CargoEntityServiceImplTest {
    @Autowired
    FlightEntityService flightEntityService;
    @Autowired
    CargoEntityService cargoEntityService;

    DateTimeFormatter formatter;

    @BeforeEach
    void init() {
        DataInit dataLoader = new DataInit(flightEntityService, cargoEntityService);
        dataLoader.loadData(Constants.FILE_TEST_FLIGHT_JSON, Constants.FILE_TEST_CARGO_JSON);
        formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
    }

    @Test
    void save() {
        List<CargoEntity> testCargoEntity = new LinkedList<>();
        testCargoEntity.add(new CargoEntity());
        testCargoEntity.add(new CargoEntity());

        cargoEntityService.save(testCargoEntity);
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