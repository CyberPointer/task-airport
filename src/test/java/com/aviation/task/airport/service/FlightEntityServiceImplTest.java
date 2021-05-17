package com.aviation.task.airport.service;

import com.aviation.task.airport.bootstrap.DataInit;
import com.aviation.task.airport.constants.Constants;
import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FlightEntityServiceImplTest {

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
    void testSaveAndFindAllFlights() {
        List<FlightEntity> testFlightEntity = new LinkedList<>();
        testFlightEntity.add(new FlightEntity());
        testFlightEntity.add(new FlightEntity());
        flightEntityService.save(testFlightEntity);

        assertEquals(2, flightEntityService.findAllFlights().size());
    }

    @Test
    void testSaveAndFindAllFlightsEmpty() {
        List<FlightEntity> tmpFlights = new ArrayList<>();
        flightEntityService.save(tmpFlights);

        assertEquals(0, flightEntityService.findAllFlights().size());
    }

    @Test
    void testFindIdByFlightNumberAndDate() {
        Integer expectedId = 0;
        Integer flightNumber = 4380;
        ZonedDateTime date = ZonedDateTime.parse("2017-01-22T02:27:51 -01:00", formatter);

        assertEquals(expectedId, flightEntityService.findIdByFlightNumberAndDate(flightNumber, date));
    }


    @Test
    void testFindIdByFlightNumberAndDateAsString() {
        Integer expectedId = 1;
        Integer flightNumber = 8074;
        String date = "2018-08-30T08:05:36 -02:00";

        assertEquals(1, flightEntityService.findIdByFlightNumberAndDate(flightNumber, date));
    }

    @Test
    void testFindIdByFlightNumberAndDateNotFound() {
        Integer expectedId = -1;
        Integer flightNumber = 4330;
        ZonedDateTime date = ZonedDateTime.parse("2017-01-22T02:27:51 -01:00", formatter);

        assertEquals(expectedId, flightEntityService.findIdByFlightNumberAndDate(flightNumber, date));
    }

    @Test
    void testFlightsInfoByStringOneArrivingAndLeaving() {
        Integer flightsDeparting = 1;
        Integer flightsArriving = 1;
        Integer baggageArrivingPieces = 1003;
        Integer baggageDepartingPieces = 4274;

        String iATACode = "LAX";
        String date = "2018-08-30T08:05:36 -02:00";

        AirportInfo airportInfo = flightEntityService.flightsInfo(iATACode, date);

        assertEquals(flightsArriving, airportInfo.getFlightsArriving().intValue());
        assertEquals(flightsDeparting, airportInfo.getFlightsDeparting().intValue());
        assertEquals(baggageArrivingPieces, airportInfo.getBaggageArrivingPieces());
        assertEquals(baggageDepartingPieces, airportInfo.getBaggageDepartingPieces());
    }

    @Test
    void testFlightsInfoByDateOneArrivingAndLeaving() {
        Integer flightsDeparting = 1;
        Integer flightsArriving = 1;
        Integer baggageArrivingPieces = 1003;
        Integer baggageDepartingPieces = 4274;

        String iATACode = "LAX";
        ZonedDateTime date = ZonedDateTime.parse("2018-08-30T08:05:36 -02:00", formatter);

        AirportInfo airportInfo = flightEntityService.flightsInfo(iATACode, date);

        assertEquals(flightsArriving, airportInfo.getFlightsArriving().intValue());
        assertEquals(flightsDeparting, airportInfo.getFlightsDeparting().intValue());
        assertEquals(baggageArrivingPieces, airportInfo.getBaggageArrivingPieces());
        assertEquals(baggageDepartingPieces, airportInfo.getBaggageDepartingPieces());
    }

    @Test
    void testFlightsInfoByDateOneDeparting() {
        Integer flightsDeparting = 1;
        Integer flightsArriving = 0;
        Integer baggageArrivingPieces = 0;
        Integer baggageDepartingPieces = 1003;

        String iATACode = "YYT";
        ZonedDateTime date = ZonedDateTime.parse("2018-08-30T08:05:36 -02:00", formatter);

        AirportInfo airportInfo = flightEntityService.flightsInfo(iATACode, date);

        assertEquals(flightsArriving, airportInfo.getFlightsArriving().intValue());
        assertEquals(flightsDeparting, airportInfo.getFlightsDeparting().intValue());
        assertEquals(baggageArrivingPieces, airportInfo.getBaggageArrivingPieces());
        assertEquals(baggageDepartingPieces, airportInfo.getBaggageDepartingPieces());
    }

    @Test
    void testFlightsInfoByDateNotFound() {
        Integer flightsDeparting = 0;
        Integer flightsArriving = 0;
        Integer baggageArrivingPieces = null;
        Integer baggageDepartingPieces = null;

        String iATACode = "ANC";
        ZonedDateTime date = ZonedDateTime.parse("2019-08-30T08:05:36 -02:00", formatter);

        AirportInfo airportInfo = flightEntityService.flightsInfo(iATACode, date);

        assertEquals(flightsArriving, airportInfo.getFlightsArriving().intValue());
        assertEquals(flightsDeparting, airportInfo.getFlightsDeparting().intValue());
        assertEquals(baggageArrivingPieces, airportInfo.getBaggageArrivingPieces());
        assertEquals(baggageDepartingPieces, airportInfo.getBaggageDepartingPieces());
    }
}
