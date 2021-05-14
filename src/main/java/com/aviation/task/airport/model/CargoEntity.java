
package com.aviation.task.airport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CargoEntity {

    @SerializedName("flightId")
    @Expose
    private Integer flightId;
    @SerializedName("baggage")
    @Expose
    private List<Baggage> baggage = null;
    @SerializedName("cargo")
    @Expose
    private List<Cargo> cargo = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CargoEntity() {
    }

    /**
     * 
     * @param baggage
     * @param flightId
     * @param cargo
     */
    public CargoEntity(Integer flightId, List<Baggage> baggage, List<Cargo> cargo) {
        super();
        this.flightId = flightId;
        this.baggage = baggage;
        this.cargo = cargo;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public List<Baggage> getBaggage() {
        return baggage;
    }

    public void setBaggage(List<Baggage> baggage) {
        this.baggage = baggage;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "CargoEntity{" +
                "flightId=" + flightId +
                ", baggage=" + baggage +
                ", cargo=" + cargo +
                '}';
    }
}
