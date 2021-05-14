package com.aviation.task.airport.bootstrap;

import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.CargoEntityServiceImpl;
import com.aviation.task.airport.service.FlightEntityService;
import com.aviation.task.airport.service.FlightEntityServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DataInitTest {

    FlightEntityService flightEntityService;
    CargoEntityService cargoEntityService;
    DataInit dataInit;

    Gson gson;
    private final String FILE_FLIGHT_JSON = "src/test/resources/json/testflight.json";
    private final String FILE_CARGO_JSON = "src/test/resources/json/testcargo.json";

    @BeforeEach
    void init() {
        cargoEntityService = new CargoEntityServiceImpl();
        flightEntityService = new FlightEntityServiceImpl(cargoEntityService);
    }

    @Test
    void loadData() {
        dataInit = new DataInit(flightEntityService, cargoEntityService, FILE_FLIGHT_JSON, FILE_CARGO_JSON);
        dataInit.loadData();

        assertEquals(3, cargoEntityService.findAllCargo().size());
        assertEquals(3, flightEntityService.findAllFlights().size());
    }
}