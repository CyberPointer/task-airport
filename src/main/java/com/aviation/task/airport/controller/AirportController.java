package com.aviation.task.airport.controller;

import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightInfo;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;

@RequestMapping("api/")
@RestController
@Validated
public class AirportController {
    FlightEntityService flightEntityService;
    CargoEntityService cargoEntityService;
    private final String AIRPORT_IATA_PATTERN = "SEA|YYZ|YYT|ANC|LAX|MIT|LEW|GDN|KRK|PPX";

    public AirportController(FlightEntityService flightEntityService, CargoEntityService cargoEntityService) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
    }

    @GetMapping("/flight/{flightNum}/date/{date}")
    public FlightInfo getFlightInfo(@PathVariable @Min(1000) @Max(9999) Integer flightNum,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss XXX") ZonedDateTime date){
        System.out.println(flightNum + " " + date);
        int flightId = flightEntityService.findIdByFlightNumberAndDate(flightNum, date);
        FlightInfo flightInfo =cargoEntityService.calculateTotalWeight(flightId);
        return  flightInfo;

    }

    @GetMapping("/airport/{iATAcode}/date/{date}")
    public AirportInfo getAirportInfo(@PathVariable @Pattern(regexp = AIRPORT_IATA_PATTERN) String iATAcode,
                                      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss XXX") ZonedDateTime date){
        System.out.println(iATAcode + " " + date);
        AirportInfo airportInfo = flightEntityService.flightsInfo(iATAcode, date);
        return airportInfo;
    }
}