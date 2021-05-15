package com.aviation.task.airport.service;

import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.Baggage;
import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class FlightEntityServiceImpl implements FlightEntityService {

    private List<FlightEntity> flightEntityList;
    private static List<CargoEntity> cargoEntityList;
    CargoEntityService cargoEntityService;

    private final Logger log = LoggerFactory.getLogger(FlightEntityServiceImpl.class);

    public FlightEntityServiceImpl(CargoEntityService cargoEntityService) {
        this.cargoEntityService = cargoEntityService;
        log.info("constructing FlightEntityServiceImpl");
    }

    @Override
    public void save(List<FlightEntity> flight) {
        log.info("saving flightEntities");
        flightEntityList = new LinkedList<>(flight);
    }

    @Override
    public List<FlightEntity> findAllFlights() {
        log.info("finding flightEntities");
        return flightEntityList;
    }

    private ZonedDateTime parseDate(String date) {
        log.info("parsing date from string to ZonedDateTime");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
        return ZonedDateTime.parse(date, format);
    }

    private long countFlightsDeparting(String iATACode, ZonedDateTime date) {
        log.info("counting number of flights departing");
        return flightEntityList.stream()
                .filter(flight ->
                        (flight.getDepartureAirportIATACode().equals(iATACode)) && (flight.getDepartureDate().equals(date)))
                .count();
    }

    private long countFlightsArriving(String iATACode, ZonedDateTime date) {
        log.info("counting number of flights arriving");
        return flightEntityList
                .stream()
                .filter(flight ->
                        (flight.getArrivalAirportIATACode().equals(iATACode)) && flight.getDepartureDate().equals(date))
                .count();
    }

    private int summarizePiecesOfBaggageDeparting(String iATACode, ZonedDateTime date) {
        log.info("summarizing pieces of baggage departing");
        return flightEntityList
                .stream()
                .filter(flight ->
                        (flight.getDepartureAirportIATACode().equals(iATACode)) && (flight.getDepartureDate().equals(date)))
                .map(FlightEntity::getFlightId)
                .flatMapToInt(id -> cargoEntityList
                        .stream()
                        .filter(cargoEntity -> cargoEntity.getFlightId() == id)
                        .flatMap(cargoEntity -> cargoEntity.getBaggage().stream())
                        .mapToInt(Baggage::getPieces))
                .sum();

    }

    private int summarizePiecesOfBaggageArriving(String iATACode, ZonedDateTime date) {
        log.info("summarizing pieces of baggage arriving");
        return flightEntityList
                .stream()
                .filter(flight ->
                        (flight.getArrivalAirportIATACode().equals(iATACode)) && (flight.getDepartureDate().equals(date)))
                .map(FlightEntity::getFlightId)
                .flatMapToInt(id -> cargoEntityList
                        .stream()
                        .filter(cargoEntity -> cargoEntity.getFlightId() == id)
                        .flatMap(cargoEntity -> cargoEntity.getBaggage().stream())
                        .mapToInt(Baggage::getPieces))
                .sum();
    }


    public AirportInfo flightsInfo(String iATACode, String date) {
        return flightsInfo(iATACode, parseDate(date));
    }

//    Number of flights departing from this airport
//    Number of flights arriving to this airport
//    Total number (pieces) of baggage arriving to this airport
//    Total number (pieces) of baggage departing from this airport.
    public AirportInfo flightsInfo(String iATACode, ZonedDateTime date) {
        log.info("calculating flights information");
        cargoEntityList = cargoEntityService.findAllCargo();
        AirportInfo airportInfo = new AirportInfo();

        airportInfo.setFlightsDeparting(countFlightsDeparting(iATACode, date));
        airportInfo.setFlightsArriving(countFlightsArriving(iATACode, date));
        airportInfo.setBaggageDepartingPieces(summarizePiecesOfBaggageDeparting(iATACode, date));
        airportInfo.setBaggageArrivingPieces(summarizePiecesOfBaggageArriving(iATACode, date));

        return airportInfo;
    }

    public int findIdByFlightNumberAndDate(int flightNumber, String date) {
        return findIdByFlightNumberAndDate(flightNumber, parseDate(date));
    }


    public int findIdByFlightNumberAndDate(int flightNumber, ZonedDateTime date) {
        log.info("finding flightId");
        int flightId;
        return flightId = flightEntityList.stream()
                .filter(flight ->
                        (flight.getFlightNumber() == flightNumber) && (flight.getDepartureDate().equals(date)))         //filtering flights by flight number & date
                .mapToInt(FlightEntity::getFlightId)                                                                    //getting flight number's id
                .findFirst()
                .orElse(-1);
    }
}
