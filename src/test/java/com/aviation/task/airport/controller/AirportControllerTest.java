package com.aviation.task.airport.controller;

import com.aviation.task.airport.model.AirportInfo;
import com.aviation.task.airport.model.FlightInfo;
import com.aviation.task.airport.service.CargoEntityService;
import com.aviation.task.airport.service.FlightEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AirportController.class)
class AirportControllerTest {
    @MockBean
    FlightEntityService flightEntityService;
    @MockBean
    CargoEntityService cargoEntityService;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AirportController(flightEntityService, cargoEntityService)).build();
    }

    @Test
    void getFlightInfo() throws Exception {
        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setTotalWeight(1000L);
        flightInfo.setCargoWeight(500L);
        flightInfo.setBaggageWeight(500L);

        Integer flightNumber = 5225;
        String date = "2019-07-15T07:15:45 -02:00";

        when(cargoEntityService.calculateTotalWeight(anyInt())).thenReturn(flightInfo);

        mockMvc.perform(get("/api/flight/"+flightNumber+"/date/"+date+"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cargoWeight").value(500))
                .andExpect(jsonPath("$.baggageWeight").value(500))
                .andExpect(jsonPath("$.totalWeight").value(1000));

        verify(cargoEntityService, times(1)).calculateTotalWeight(anyInt());
        verifyNoMoreInteractions(cargoEntityService);
    }

    @Test
    void getAirportInfo() throws Exception {
        AirportInfo airportInfo = new AirportInfo();
        airportInfo.setFlightsDeparting(2L);
        airportInfo.setFlightsArriving(1L);
        airportInfo.setBaggageArrivingPieces(1000);
        airportInfo.setBaggageDepartingPieces(0);

        String iATAcode = "LEW";
        String date = "2018-08-30T08:05:36 -02:00";

        when(flightEntityService.flightsInfo(anyString(),any(ZonedDateTime.class))).thenReturn(airportInfo);

        mockMvc.perform(get("/api/airport/"+iATAcode+"/date/"+date+"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightsDeparting").value(2))
                .andExpect(jsonPath("$.flightsArriving").value(1))
                .andExpect(jsonPath("$.baggageArrivingPieces").value(1000))
                .andExpect(jsonPath("$.baggageDepartingPieces").value(0));

        verify(flightEntityService, times(1)).flightsInfo(anyString(),any(ZonedDateTime.class));
        verifyNoMoreInteractions(flightEntityService);
    }
}