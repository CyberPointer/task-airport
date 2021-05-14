package com.aviation.task.airport.bootstrap;

import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    private final String FILE_FLIGHT_JSON = "src/main/resources/json/flight.json";
    private final String FILE_CARGO_JSON = "src/main/resources/json/cargo.json";

    FlightEntityService flightEntityService;
    CargoEntityService cargoEntityService;

    DataLoader(FlightEntityService flightEntityService, CargoEntityService cargoEntityService) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
    }

    public static String createJsonFlightEntityString(Integer flightId, Integer flightNumber, String departureAirportIATACode, String arrivalAirportIATACode,
                                                      String departureDate) {
        return "[{\"flightId\":\"" + flightId + "\","
                + "\"flightNumber\":\"" + flightNumber
                + "\",\"departureAirportIATACode\":\"" + departureAirportIATACode
                + "\",\"arrivalAirportIATACode\":\"" + arrivalAirportIATACode + "\","
                + "\"departureDate\":\"" + departureDate + "\"}]";
    }

    @Override
    public void run(String... args) {
        DataInit dataInit = new DataInit(flightEntityService, cargoEntityService, FILE_FLIGHT_JSON, FILE_CARGO_JSON);
        dataInit.loadData();
    }
}

