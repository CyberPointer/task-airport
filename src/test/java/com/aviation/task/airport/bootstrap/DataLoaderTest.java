package com.aviation.task.airport.bootstrap;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



class DataLoaderTest {

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
}