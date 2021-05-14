package com.aviation.task.airport.service;

import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightInfo;

import java.util.List;

public interface CargoEntityService {
    public void save(List<CargoEntity> cargo);
    public List<CargoEntity> findAllCargo();
    public long calculateCargoWeight(Integer flightId);
    public long calculateBaggageWeight(Integer flightId);
    public FlightInfo calculateTotalWeight(Integer flightId);


}
