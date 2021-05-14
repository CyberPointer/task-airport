package com.aviation.task.airport.service;

import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightInfo;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CargoEntityServiceImpl implements CargoEntityService {
    private final double LBS_TO_KG = 0.4535924;
    private List<CargoEntity> cargoEntities;
    private FlightInfo flightInfo;

    @Override
    public void save(List<CargoEntity> cargo) {
        this.cargoEntities = new LinkedList<>(cargo);
    }

    @Override
    public List<CargoEntity> findAllCargo() {
        return cargoEntities;
    }

    public long calculateCargoWeight(Integer flightId) {

        long cargoWeight = cargoEntities
                .stream()
                .filter(cargoEntity -> cargoEntity.getFlightId() == flightId)                                           //filtering cargos by flightId
                .map(CargoEntity::getCargo)                                                             //changing operating object from cargoEntity to list of cargos
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
        flightInfo = new FlightInfo();
        flightInfo.setBaggageWeight(calculateBaggageWeight(flightId));
        flightInfo.setCargoWeight(calculateCargoWeight(flightId));
        flightInfo.setTotalWeight(flightInfo.getBaggageWeight() + flightInfo.getCargoWeight());
        return flightInfo;
    }
}
