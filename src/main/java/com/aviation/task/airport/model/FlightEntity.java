package com.aviation.task.airport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;


public class FlightEntity {

    @SerializedName("flightId")
    @Expose
    private Integer flightId;
    @SerializedName("flightNumber")
    @Expose
    private Integer flightNumber;
    @SerializedName("departureAirportIATACode")
    @Expose
    private String departureAirportIATACode;
    @SerializedName("arrivalAirportIATACode")
    @Expose
    private String arrivalAirportIATACode;
    @SerializedName("departureDate")
    @Expose
    private ZonedDateTime departureDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public FlightEntity() {
    }

    public FlightEntity(Integer flightId, Integer flightNumber, String departureAirportIATACode, String arrivalAirportIATACode, ZonedDateTime departureDate) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departureAirportIATACode;
        this.arrivalAirportIATACode = arrivalAirportIATACode;
        this.departureDate = departureDate;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAirportIATACode() {
        return departureAirportIATACode;
    }

    public void setDepartureAirportIATACode(String departureAirportIATACode) {
        this.departureAirportIATACode = departureAirportIATACode;
    }

    public String getArrivalAirportIATACode() {
        return arrivalAirportIATACode;
    }

    public void setArrivalAirportIATACode(String arrivalAirportIATACode) {
        this.arrivalAirportIATACode = arrivalAirportIATACode;
    }

    public ZonedDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(ZonedDateTime departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "flightId=" + flightId +
                ", flightNumber=" + flightNumber +
                ", departureAirportIATACode='" + departureAirportIATACode + '\'' +
                ", arrivalAirportIATACode='" + arrivalAirportIATACode + '\'' +
                ", departureDate=" + departureDate +
                '}';
    }
}
