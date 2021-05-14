package com.aviation.task.airport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightInfo {
    @SerializedName("cargoWeight")
    @Expose
    private Long cargoWeight;
    @SerializedName("baggageWeight")
    @Expose
    private Long baggageWeight;
    @SerializedName("totalWeight")
    @Expose
    private Long totalWeight;

    public FlightInfo() {

    }

    public FlightInfo(Long cargoWeight, Long baggageWeight, Long totalWeight) {
        this.cargoWeight = cargoWeight;
        this.baggageWeight = baggageWeight;
        this.totalWeight = totalWeight;
    }

    public Long getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Long cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Long getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(Long baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    public Long getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Long totalWeight) {
        this.totalWeight = totalWeight;
    }

    @Override
    public String toString() {
        return "FlightInfo{" +
                "cargoWeight=" + cargoWeight +
                ", baggageWeight=" + baggageWeight +
                ", totalWeight=" + totalWeight +
                '}';
    }
}