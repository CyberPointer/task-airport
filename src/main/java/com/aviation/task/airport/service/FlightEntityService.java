package com.aviation.task.airport.service;

import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface FlightEntityService {
    public void save(List<FlightEntity> flight);
    public List<FlightEntity> findAllFlights();
    public int findIdByFlightNumberAndDate(int flightNumber, String date);
    public int findIdByFlightNumberAndDate(int flightNumber, ZonedDateTime date);
    public AirportInfo flightsInfo(String iATACode, String date);
    public AirportInfo flightsInfo(String iATACode, ZonedDateTime date);

}
