package com.shippable.properties.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shippable.properties.model.PropertiesVessel.Property;

public class PropertySerializer extends JsonSerializer<Property> {
	public void serialize(Property property, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeObjectField(property.getName(), property.getValue());
		jgen.writeEndObject();
	}
}
