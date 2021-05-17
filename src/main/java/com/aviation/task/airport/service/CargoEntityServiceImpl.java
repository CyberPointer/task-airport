package com.aviation.task.airport.service;

import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CargoEntityServiceImpl implements CargoEntityService {

    private final double LBS_TO_KG = 0.4535924;
    private List<CargoEntity> cargoEntities;
    private FlightInfo flightInfo;

    private Logger log = LoggerFactory.getLogger(CargoEntityServiceImpl.class);

    @Override
    public void save(List<CargoEntity> cargo) {
        log.info("saving cargo entities");
        this.cargoEntities = new LinkedList<>(cargo);
    }

    @Override
    public List<CargoEntity> findAllCargo() {
        log.info("finding cargo entities");
        return cargoEntities;
    }

    public long calculateCargoWeight(Integer flightId) {
        log.info("calculating cargo weight");

        long cargoWeight = cargoEntities
                .stream()
                .filter(cargoEntity -> cargoEntity.getFlightId() == flightId)                                           //filtering cargos by flightId
                .map(CargoEntity::getCargo)                                                                             //changing operating object from cargoEntity to list of cargos
                .flatMapToLong(cargos ->
                        cargos.stream()
                                .mapToLong(cargo -> {
                                    return cargo.getWeightUnit().equals("kg")
                                            ? cargo.getWeight() : (int) (cargo.getWeight() * LBS_TO_KG);
                                })
                )                                                                                                       //creating sub stream of cargos then calculating cargo weight in kg
                .sum();                                                                                                 //summing all cargo weight
        return cargoWeight;
    }

    public long calculateBaggageWeight(Integer flightId) {
        log.info("calculating baggage weight");

        long baggageWeight = cargoEntities
                .stream()
                .filter(cargoEntity -> cargoEntity.getFlightId() == flightId)                                           //filtering cargos by flightId
                .map(CargoEntity::getBaggage)                                                                           //changing object from cargoEntity to list of cargos
                .flatMapToLong(baggages ->
                        baggages.stream()
                                .mapToLong(baggage -> {
                                    return baggage.getWeightUnit().equals("kg")
                                            ? baggage.getWeight() : (int) (baggage.getWeight() * LBS_TO_KG);
                                })
                )                                                                                                       //creating sub stream of cargos then calculating cargo weight in kg
                .sum();                                                                                                 //summing all cargo weight
        return baggageWeight;
    }

    public FlightInfo calculateTotalWeight(Integer flightId) {
        log.info("calculating total weight");

        flightInfo = new FlightInfo();
        flightInfo.setWeightUnit("kg");
        flightInfo.setBaggageWeight(calculateBaggageWeight(flightId));
        flightInfo.setCargoWeight(calculateCargoWeight(flightId));
        flightInfo.setTotalWeight(flightInfo.getBaggageWeight() + flightInfo.getCargoWeight());
        return flightInfo;
    }
}
