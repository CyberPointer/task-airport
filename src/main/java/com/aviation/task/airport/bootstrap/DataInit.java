package com.aviation.task.airport.bootstrap;

import com.aviation.task.airport.converter.ZonedDateTimeConverter;
import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightEntity;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class DataInit {

    private FlightEntityService flightEntityService;
    private CargoEntityService cargoEntityService;
    private Gson gson;
    private Logger log = LoggerFactory.getLogger(DataInit.class);

    public DataInit(FlightEntityService flightEntityService, CargoEntityService cargoEntityService) {
        this.flightEntityService = flightEntityService;
        this.cargoEntityService = cargoEntityService;
        log.info("Constructing DataInit");
    }

    public void loadData(String FILE_FLIGHT_JSON, String FILE_CARGO_JSON) {
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
            log.error("Error initializing data");
            e.printStackTrace();
        }
    }
}
