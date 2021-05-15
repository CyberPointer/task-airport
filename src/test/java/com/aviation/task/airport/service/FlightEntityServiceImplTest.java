package com.aviation.task.airport.service;

import com.aviation.task.airport.bootstrap.DataInit;
import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FlightEntityServiceImplTest {

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
    void testSaveAndFindAllFlights() {
        assertEquals(3, flightEntityService.findAllFlights().size());
        flightEntityService.findAllFlights().remove(2);
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
    void testFlightsInfoByStringOneArrivingAndLeaving() {
        Integer flightsDeparting = 1;
        Integer flightsArriving = 1;
        Integer baggageArrivingPieces=1003;
        Integer baggageDepartingPieces=4274;

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
        Integer baggageArrivingPieces=1003;
        Integer baggageDepartingPieces=4274;

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
        Integer baggageArrivingPieces=0;
        Integer baggageDepartingPieces=1003;

        String iATACode = "YYT";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
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
        Integer baggageArrivingPieces=0;
        Integer baggageDepartingPieces=0;

        String iATACode = "ANC";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
        ZonedDateTime date = ZonedDateTime.parse("2019-08-30T08:05:36 -02:00", formatter);

        AirportInfo airportInfo = flightEntityService.flightsInfo(iATACode, date);

        assertEquals(flightsArriving, airportInfo.getFlightsArriving().intValue());
        assertEquals(flightsDeparting, airportInfo.getFlightsDeparting().intValue());
        assertEquals(baggageArrivingPieces, airportInfo.getBaggageArrivingPieces());
        assertEquals(baggageDepartingPieces, airportInfo.getBaggageDepartingPieces());
    }
}
