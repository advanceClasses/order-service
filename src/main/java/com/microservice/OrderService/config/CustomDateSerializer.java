package com.microservice.OrderService.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class CustomDateSerializer extends JsonSerializer<Date> {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss 'WIB'", new Locale("id", "ID"));

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(formatter.format(value));
    }
}
