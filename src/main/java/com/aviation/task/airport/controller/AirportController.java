package com.aviation.task.airport.controller;

import com.aviation.task.airport.exception.ApiRequestException;
import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightInfo;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.time.format.DateTimeFormatter;

@RequestMapping("api/")
@RestController
@Validated
public class AirportController {
    FlightEntityService flightEntityService;
    CargoEntityService cargoEntityService;

    private Logger log = LoggerFactory.getLogger(AirportController.class);

    private final String AIRPORT_IATA_PATTERN = "SEA|YYZ|YYT|ANC|LAX|MIT|LEW|GDN|KRK|PPX";

    public AirportController(FlightEntityService flightEntityService, CargoEntityService cargoEntityService) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
        log.info("constructing airportController");
    }

    private boolean isDateValid(ZonedDateTime dateTime){
        String date = "2014-01-01T00:00:00 -00:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, dateTimeFormatter);
        return dateTime.isAfter(zonedDateTime);
    }

    @GetMapping("/flight/{flightNum}/date/{date}")
    public FlightInfo getFlightInfo(@PathVariable @Min(1000) @Max(9999) Integer flightNum,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss XXX") ZonedDateTime date) throws ApiRequestException {

        log.info("processing getFlightInfo ");
        if (!isDateValid(date)) throw new ApiRequestException("Date has to be after 2014-01-01");
        int flightId = flightEntityService.findIdByFlightNumberAndDate(flightNum, date);
        FlightInfo flightInfo =cargoEntityService.calculateTotalWeight(flightId);
        return  flightInfo;

    }

    @GetMapping("/airport/{iATAcode}/date/{date}")
    public AirportInfo getAirportInfo(@PathVariable @Pattern(regexp = AIRPORT_IATA_PATTERN) String iATAcode,
                                      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss XXX") ZonedDateTime date) throws ApiRequestException {

        log.info("processing getAirportInfo ");
        if (!isDateValid(date)) throw new ApiRequestException("Date has to be after 2014-01-01");
        AirportInfo airportInfo = flightEntityService.flightsInfo(iATAcode, date);
        return airportInfo;
    }
}
