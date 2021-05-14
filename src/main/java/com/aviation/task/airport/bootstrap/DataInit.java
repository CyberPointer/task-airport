package com.aviation.task.airport.bootstrap;

import com.aviation.task.airport.converter.ZonedDateTimeConverter;
import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightEntity;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class DataInit {

    FlightEntityService flightEntityService;
    CargoEntityService cargoEntityService;

    Gson gson;
    private final String FILE_FLIGHT_JSON;
    private final String FILE_CARGO_JSON;


    public DataInit(FlightEntityService flightEntityService, CargoEntityService cargoEntityService, String FILE_FLIGHT_JSON,  String FILE_CARGO_JSON) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
        this.FILE_FLIGHT_JSON = FILE_FLIGHT_JSON;
        this.FILE_CARGO_JSON = FILE_CARGO_JSON;
    }

    public void loadData() {

        try (FileInputStream fileFlight = new FileInputStream(FILE_FLIGHT_JSON);
             FileInputStream fileCargo = new FileInputStream(FILE_CARGO_JSON);
             InputStreamReader flightReader = new InputStreamReader(fileFlight);
             InputStreamReader cargoReader = new InputStreamReader(fileCargo)) {

            Type flightEntityType = new TypeToken<ArrayList<FlightEntity>>() {
            }.getType();
            Type cargoEntityType = new TypeToken<ArrayList<CargoEntity>>() {
            }.getType();

            gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter()).create();

            flightEntityService.save(gson.fromJson(flightReader, flightEntityType));
            cargoEntityService.save(gson.fromJson(cargoReader, cargoEntityType));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
