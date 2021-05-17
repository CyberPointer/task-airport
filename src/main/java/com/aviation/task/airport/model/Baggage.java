
package com.aviation.task.airport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Baggage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("weightUnit")
    @Expose
    private String weightUnit;
    @SerializedName("pieces")
    @Expose
    private Integer pieces;

    /**
     * No args constructor for use in serialization
     */
    public Baggage() {
    }

    /**
     * @param pieces
     * @param weight
     * @param id
     * @param weightUnit
     */
    public Baggage(Integer id, Integer weight, String weightUnit, Integer pieces) {
        super();
        this.id = id;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.pieces = pieces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    @Override
    public String toString() {
        return "Baggage{" +
                "id=" + id +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                ", pieces=" + pieces +
                '}';
    }
}
