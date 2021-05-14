package com.aviation.task.airport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AirportInfo {

    @SerializedName("flightsDeparting")
    @Expose
    private Long flightsDeparting;
    @SerializedName("flightsArriving")
    @Expose
    private Long flightsArriving;
    @SerializedName("baggageArrivingPieces")
    @Expose
    private Integer baggageArrivingPieces;
    @SerializedName("baggageDepartingPieces")
    @Expose
    private Integer baggageDepartingPieces;

    public AirportInfo(){

    }

    public AirportInfo(Long flightsDeparting, Long flightsArriving, Integer baggageArrivingPieces, Integer baggageDepartingPieces) {
        this.flightsDeparting = flightsDeparting;
        this.flightsArriving = flightsArriving;
        this.baggageArrivingPieces = baggageArrivingPieces;
        this.baggageDepartingPieces = baggageDepartingPieces;
    }

    public Long getFlightsDeparting() {
        return flightsDeparting;
    }

    public void setFlightsDeparting(Long flightsDeparting) {
        this.flightsDeparting = flightsDeparting;
    }

    public Long getFlightsArriving() {
        return flightsArriving;
    }

    public void setFlightsArriving(Long flightsArriving) {
        this.flightsArriving = flightsArriving;
    }

    public Integer getBaggageArrivingPieces() {
        return baggageArrivingPieces;
    }

    public void setBaggageArrivingPieces(Integer baggageArrivingPieces) {
        this.baggageArrivingPieces = baggageArrivingPieces;
    }

    public Integer getBaggageDepartingPieces() {
        return baggageDepartingPieces;
    }

    public void setBaggageDepartingPieces(Integer baggageDepartingPieces) {
        this.baggageDepartingPieces = baggageDepartingPieces;
    }

    @Override
    public String toString() {
        return "AirportInfo{" +
                "flightsDeparting=" + flightsDeparting +
                ", flightsArriving=" + flightsArriving +
                ", baggageArrivingPieces=" + baggageArrivingPieces +
                ", baggageDepartingPieces=" + baggageDepartingPieces +
                '}';
    }
}
