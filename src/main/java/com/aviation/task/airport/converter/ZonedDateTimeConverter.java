package com.aviation.task.airport.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.aviation.task.airport.constants.Constants.DATE_PATTERN;

public class ZonedDateTimeConverter implements JsonDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private Logger log = LoggerFactory.getLogger(ZonedDateTimeConverter.class);

    @Override
    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.getAsString() == null || jsonElement.getAsString().isEmpty()) {
            log.info("json empty or null");
            return null;
        }
        return ZonedDateTime.parse(jsonElement.getAsString(), FORMATTER);
    }
}
