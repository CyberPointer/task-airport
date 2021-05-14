package com.aviation.task.airport.bootstrap;


import com.aviation.task.airport.converter.ZonedDateTimeConverter;
import com.aviation.task.airport.model.CargoEntity;
import com.aviation.task.airport.model.FlightEntity;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



class DataLoaderTest {

    @Autowired
    FlightEntityService flightEntityService;
    @Autowired
    CargoEntityService cargoEntityService;

    private final String FILE_FLIGHT_JSON = "src/test/resources/json/testflight.json";
    private final String FILE_CARGO_JSON = "src/test/resources/json/testcargo.json";

    Gson gson;

    @Test
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

    @Test
    void testCreateJsonFlightEntityString() {
        String jsonObject = "[{\"flightId\":\"0\","
                + "\"flightNumber\":\"7103\",\"departureAirportIATACode\":\"GDN\",\"arrivalAirportIATACode\":\"YYT\","
                + "\"departureDate\":\"2021-02-06T04:15:29 -01:00\"}]";

        String jsonFlightString =  DataLoader.createJsonFlightEntityString(0, 7103, "GDN","YYT","2021-02-06T04:15:29 -01:00" );
        assertEquals(jsonObject, jsonFlightString);
    }

    @Test
    void testCreateJsonFlightEntityStringNotSame() {
        String jsonObject = "[{\"flightId\":\"1\","
                + "\"flightNumber\":\"7104\",\"departureAirportIATACode\":\"GDN\",\"arrivalAirportIATACode\":\"YYT\","
                + "\"departureDate\":\"2021-02-06T04:15:29 -01:00\"}]";

        String jsonFlightString =  DataLoader.createJsonFlightEntityString(0, 7103, "GDN","YYT","2021-02-06T04:15:29 -01:00" );
        assertNotEquals(jsonObject, jsonFlightString);
    }

 

    @Test
    void testLoad() {

        try (FileInputStream fileFlight = new FileInputStream(FILE_FLIGHT_JSON);
             FileInputStream fileCargo = new FileInputStream(FILE_CARGO_JSON);
             InputStreamReader flightReader = new InputStreamReader(fileFlight);
             InputStreamReader cargoReader = new InputStreamReader(fileCargo)) {

            Type flightEntityType = new TypeToken<ArrayList<FlightEntity>>() {
            }.getType();
            Type cargoEntityType = new TypeToken<ArrayList<CargoEntity>>() {
            }.getType();

            //"yyyy-MM-dd'T'HH:mm:ss "
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss XXX");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format1.parse("2021-02-06T04:15:29 -01:00");
                date2 = format1.parse("2021-02-06T04:15:29 -02:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss XXX");
            ZonedDateTime dateTime = ZonedDateTime.parse("2021-02-06T04:15:29 -01:00", format);
            dateTime = ZonedDateTime.parse("2021-02-06T04:15:29 -01:00", format);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Gson gson2 = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
                    .create();

            List<FlightEntity> flightEntityList = gson2.fromJson(flightReader, flightEntityType);
            System.out.println("testing by zone " + flightEntityList.get(0).getDepartureDate());

            List<CargoEntity> cargoEntityList = gson2.fromJson(cargoReader, cargoEntityType);

            cargoEntityService.save(cargoEntityList);
            flightEntityService.save(flightEntityList);

            int id = 0;
            String departureIATACode = "YYZ";
            String arrivalIATACode = "GDN";
            String date = "2021-02-06T04:15:29 -01:00";
            Integer flightNumber = 7103;

            flightEntityService.flightsInfo(arrivalIATACode, date);
            cargoEntityService.calculateTotalWeight(flightEntityService.findIdByFlightNumberAndDate(flightNumber, date));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}