package com.shippable.properties.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shippable.properties.model.PropertiesVessel;

//@EnableWebMvc
@Controller
public class PropertiesController {
	Map<String, PropertiesVessel> propertiesMap = new HashMap<>();
	Map<String, PropertiesVessel> namesMap = new HashMap<>();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showRoot(HttpServletRequest request) {
		return showIndex(request);
	}
	
	private PropertiesVessel getNamesMapProperties(String remoteAddr) {
		PropertiesVessel properties = namesMap.get(remoteAddr);
		if(properties == null) {
			System.out.println("Creating new names properties map for " + remoteAddr);
			properties = new PropertiesVessel();
			namesMap.put(remoteAddr, properties);
		}
		return properties;
	}
	
	private PropertiesVessel getPropertiesMapProperties(String remoteAddr) {
		PropertiesVessel properties = propertiesMap.get(remoteAddr);
		if(properties == null) {
			System.out.println("Creating new properties map for " + remoteAddr);
			properties = new PropertiesVessel();
			propertiesMap.put(remoteAddr, properties);
		}
		return properties;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView showIndex(HttpServletRequest request) {

		PropertiesVessel properties = getNamesMapProperties(request.getRemoteAddr());
		
		PropertiesVessel.Property firstNameProperty = properties.getProperty("firstName");
		if(firstNameProperty.getValue() == null) {
			properties.setProperty("firstName", "Unknown");
		}
		
		PropertiesVessel.Property lastNameProperty = properties.getProperty("lastName");
		if(lastNameProperty.getValue() == null) {
			properties.setProperty("lastName", "Unknown");
		}
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("firstName", properties.getProperty("firstName").getValue());
		mv.addObject("lastName", properties.getProperty("lastName").getValue());
		return mv;
	}
	
	@RequestMapping(value ="/index", method = RequestMethod.POST)
	public ModelAndView postIndex(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			HttpServletRequest request) {

		PropertiesVessel properties = getNamesMapProperties(request.getRemoteAddr());
		
		PropertiesVessel.Property firstNameProperty = properties.getProperty("firstName");
		if(firstNameProperty.getValue() == null) {
			properties.setProperty("firstName", "Unknown");
		}
		if(firstName != null) {
			properties.setProperty("firstName", firstName);
		}
		
		PropertiesVessel.Property lastNameProperty = properties.getProperty("lastName");
		if(lastNameProperty.getValue() == null) {
			properties.setProperty("lastName", "Unknown");
		}
		if(lastName != null) {
			properties.setProperty("lastName", lastName);
		}
				
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("firstName", properties.getProperty("firstName").getValue());
		mv.addObject("lastName", properties.getProperty("lastName").getValue());
		return mv;
	}
	
	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PropertiesVessel getProperties(HttpServletRequest request) {
		PropertiesVessel properties = getPropertiesMapProperties(request.getRemoteAddr());
		
		return properties;
	}
	
	@RequestMapping(value = "/property", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PropertiesVessel putProperty(@RequestParam("name") String propertyName, @RequestParam("value") String propertyValue, HttpServletRequest request) {
		return setProperty(request.getRemoteAddr(), propertyName, propertyValue);
	}

	@RequestMapping(value = "/property", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PropertiesVessel postProperty(@RequestParam("name") String propertyName, @RequestParam("value") String propertyValue, HttpServletRequest request) {
		return setProperty(request.getRemoteAddr(), propertyName, propertyValue);
	}
	
	@RequestMapping(value = "/property", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PropertiesVessel.Property getPropertyResponse(@RequestParam("name") String propertyName, HttpServletRequest request) {
		return getProperty(request.getRemoteAddr(), propertyName);
	}
	
	private PropertiesVessel.Property getProperty(String remoteAddr, String propertyName) {
		PropertiesVessel properties = getPropertiesMapProperties(remoteAddr);
		
		return properties.getProperty(propertyName);
	}

	private PropertiesVessel setProperty(String remoteAddr, String propertyName, String propertyValue) {
		PropertiesVessel properties = getPropertiesMapProperties(remoteAddr);
		
		properties.setProperty("hostDateTime", LocalDateTime.now().toString());
		properties.setProperty(propertyName, propertyValue);
		return properties;
	}
	
}
