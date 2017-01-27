package com.shippable.properties.model;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shippable.properties.model.PropertiesVessel.Property;

public class PropertiesSerializer extends JsonSerializer<Set<Property>> {
	public void serialize(Set<Property> properties, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		//jgen.writeObjectField("size", properties.size());
		for(Property property : properties) {
			jgen.writeObjectField(property.getName(), property.getValue());
		}
		jgen.writeEndObject();
	}
}
