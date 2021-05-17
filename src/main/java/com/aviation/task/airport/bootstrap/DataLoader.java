package com.aviation.task.airport.bootstrap;

import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.aviation.task.airport.constants.Constants.FILE_CARGO_JSON;
import static com.aviation.task.airport.constants.Constants.FILE_FLIGHT_JSON;


@Component
public class DataLoader implements CommandLineRunner {

    private FlightEntityService flightEntityService;
    private CargoEntityService cargoEntityService;
    private DataInit dataInit;

    private static Logger log = LoggerFactory.getLogger(DataLoader.class);

    public DataLoader(FlightEntityService flightEntityService, CargoEntityService cargoEntityService, DataInit dataInit) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
        this.dataInit = dataInit;
    }

    public static String createJsonFlightEntityString(Integer flightId, Integer flightNumber
            , String departureAirportIATACode, String arrivalAirportIATACode, String departureDate) {
        log.info("Creating Json string");
        return "[{\"flightId\":\"" + flightId + "\","
                + "\"flightNumber\":\"" + flightNumber
                + "\",\"departureAirportIATACode\":\"" + departureAirportIATACode
                + "\",\"arrivalAirportIATACode\":\"" + arrivalAirportIATACode + "\","
                + "\"departureDate\":\"" + departureDate + "\"}]";
    }

    @Override
    public void run(String... args) {
        dataInit.loadData(FILE_FLIGHT_JSON, FILE_CARGO_JSON);
    }
}

