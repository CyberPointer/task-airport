package com.aviation.task.airport.converter;

import com.aviation.task.airport.bootstrap.DataLoader;
import com.aviation.task.airport.model.FlightEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ZonedDateTimeConverterTest {
    DateTimeFormatter format;

    @BeforeEach
    void init(){
       format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
    }

    @Test
    void deserializeSameValue() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-02-06T04:15:29 -01:00", format);

        String jsonString = DataLoader.createJsonFlightEntityString(0, 7103, "GDN","YYT","2021-02-06T04:15:29 -01:00" );
        Type flightEntityType = new TypeToken<ArrayList<FlightEntity>>() {
        }.getType();

        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter()).create();
        List<FlightEntity> flightEntity= gson.fromJson(jsonString, flightEntityType);

        assertEquals(zonedDateTime, flightEntity.get(0).getDepartureDate());

    }

    @Test
    void deserializeDifferentValue() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-02-06T04:15:29 -02:00", format);

        String jsonString = DataLoader.createJsonFlightEntityString(0, 7103, "GDN","YYT","2021-02-06T04:15:29 -01:00" );
        Type flightEntityType = new TypeToken<ArrayList<FlightEntity>>() {
        }.getType();

        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter()).create();
        List<FlightEntity> flightEntity= gson.fromJson(jsonString, flightEntityType);

        assertNotEquals(zonedDateTime, flightEntity.get(0).getDepartureDate());
    }
}