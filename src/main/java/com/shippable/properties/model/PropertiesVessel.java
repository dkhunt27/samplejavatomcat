package com.shippable.properties.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PropertiesVessel {
	@JsonSerialize(using = PropertiesSerializer.class)
	private Set<Property> properties = new HashSet<>();

	public Set<Property> getProperties() {
		return properties;
	}

	public Property getProperty(String propertyToFind) {
		for (Property property : properties) {
			if (property.getName().equals(propertyToFind)) {
				return property;
			}
		}
		return new Property(propertyToFind, null);
	}

	@JsonSerialize(using = PropertySerializer.class)
	public static class Property implements Comparable<Property> {
		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		private String name;
		private String value;

		public Property(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Property)) {
				return false;
			}

			return this.name.equals(((Property) o).getName());
		}

		@Override
		public int compareTo(Property arg0) {
			return this.name.compareTo(arg0.getName());
		}

		@Override
		public int hashCode() {
			return this.name.hashCode();
		}
	}

	public void setProperty(String name, String value) {
		Property property = new Property(name, value);
		properties.remove(property);
		properties.add(property);
	}
}
