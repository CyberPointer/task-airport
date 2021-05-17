package com.aviation.task.airport.controller;

import com.aviation.task.airport.exception.ApiRequestException;
import com.aviation.task.airport.exception.NotFoundException;
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

import static com.aviation.task.airport.constants.Constants.DATE_PATTERN;

@RequestMapping("api/")
@RestController
@Validated
public class AirportController {

    private FlightEntityService flightEntityService;
    private CargoEntityService cargoEntityService;
    private Logger log = LoggerFactory.getLogger(AirportController.class);

    private final String AIRPORT_IATA_PATTERN = "SEA|YYZ|YYT|ANC|LAX|MIT|LEW|GDN|KRK|PPX";
    private final String DATE_MINIMUM = "2014-01-01T00:00:00 -00:00";

    public AirportController(FlightEntityService flightEntityService, CargoEntityService cargoEntityService) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
        log.info("constructing airportController");
    }

    //Method validates date to be after 2014-01-01, as specified in snippet of code to generate random JSON data in task's pdf
    private boolean isDateValid(ZonedDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(DATE_MINIMUM, dateTimeFormatter);
        return dateTime.isAfter(zonedDateTime);
    }


    @GetMapping("/flight/{flightNum}/date/{date}")
    public FlightInfo getFlightInfo(@PathVariable @Min(1000) @Max(9999) Integer flightNum,
                                    @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) ZonedDateTime date)
            throws ApiRequestException {

        log.info("processing getFlightInfo ");
        if (!isDateValid(date)) {
            log.info("Date is before 2014-01-01");
            throw new ApiRequestException("Date has to be after 2014-01-01");
        }

        int flightId = flightEntityService.findIdByFlightNumberAndDate(flightNum, date);
        if (flightId == -1) {
            throw new NotFoundException("Flight not found");
        }
        FlightInfo flightInfo = cargoEntityService.calculateTotalWeight(flightId);
        return flightInfo;

    }

    @GetMapping("/airport/{iATAcode}/date/{date}")
    public AirportInfo getAirportInfo(@PathVariable @Pattern(regexp = AIRPORT_IATA_PATTERN) String iATAcode,
                                      @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) ZonedDateTime date)
            throws ApiRequestException {

        log.info("processing getAirportInfo ");
        if (!isDateValid(date)) {
            log.info("Date is before 2014-01-01");
            throw new ApiRequestException("Date has to be after 2014-01-01");
        }
        //if airport's flights departing/arriving are 0, that means airport wasn't found
        AirportInfo airportInfo = flightEntityService.flightsInfo(iATAcode, date);
        if (airportInfo.getFlightsDeparting() == 0 && airportInfo.getFlightsDeparting() == 0) {
            log.info("Airport not found");
            throw new NotFoundException("Airport not found");
        }
        return airportInfo;
    }
}
