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

import static com.aviation.task.airport.constants.Constants.DATE_PATTERN;

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
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
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

    /* Method  below is quite complex. In order to calculate pieces of baggage departing/arriving,
    first all flights ids from FlightEntityList have to be found by matching given IATA code and date.
    After that new stream pipeline of cargoEntityList within current one's FlightEntity stream has to be created,
    then all cargoEntities which are matching ids that were found before have to be filtered, from there on stream
    has to be changed to list of Baggage objects and finally from each of these Baggage objects information's
    about baggage' pieces have to be retrieved
    */
    private int summarizePiecesOfBaggageDeparting(String iATACode, ZonedDateTime date) {
        log.info("summarizing pieces of baggage departing");
        return flightEntityList
                .stream()
                .filter(flight ->
                        (flight.getDepartureAirportIATACode().equals(iATACode)) && (flight.getDepartureDate().equals(date)))
                .map(FlightEntity::getFlightId)                                     //find all flights ids matching  IATAcode and date
                .flatMapToInt(id -> cargoEntityList
                        .stream()                                                   // create new stream to match ids found before
                        .filter(cargoEntity -> cargoEntity.getFlightId() == id)
                        .flatMap(cargoEntity -> cargoEntity.getBaggage().stream())   //change stream from cargoEntity to List of Baggage objects
                        .mapToInt(Baggage::getPieces))                              // get each filtered baggage's pieces
                .sum();

    }

    /* Code is almost the same as method above, however it has to be like this because of filtering condition
     is based on IATA code arriving not departing. It is possible to make it in one method but it might be harder to read.
     */
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

    /*   Number of flights departing from this airport
       Number of flights arriving to this airport
       Total number (pieces) of baggage arriving to this airport
       Total number (pieces) of baggage departing from this airport.
     */
    public AirportInfo flightsInfo(String iATACode, ZonedDateTime date) {
        log.info("calculating flights information");
        cargoEntityList = cargoEntityService.findAllCargo();
        AirportInfo airportInfo = new AirportInfo();

        airportInfo.setFlightsDeparting(countFlightsDeparting(iATACode, date));
        airportInfo.setFlightsArriving(countFlightsArriving(iATACode, date));

    //if airport's flights departing/arriving are "found" that means they are different than 0 then proceed to additional calculation
        if (airportInfo.getFlightsArriving() != 0 || airportInfo.getFlightsDeparting() != 0) {
            airportInfo.setBaggageDepartingPieces(summarizePiecesOfBaggageDeparting(iATACode, date));
            airportInfo.setBaggageArrivingPieces(summarizePiecesOfBaggageArriving(iATACode, date));
        }

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
