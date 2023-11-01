package io.risf.sales.service.outputter.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * used internally by jackson to dictate the format of which it should serialize doubles
 */
public class DoubleSerializer extends JsonSerializer<Double> {
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(decimalFormat.format(value));
    }
}