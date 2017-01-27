import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.shippable.properties.controller.PropertiesController;
import com.shippable.properties.model.PropertiesVessel;

import junit.framework.Assert;

//
// public class SimpleTestNameInput {
//
//   @Test
//   public void testNameInput() {
//     HelloWorldController hello = new HelloWorldController();
//     ModelAndView result = hello.showMessage("Test Name");
//     Assert.assertEquals("Test Name", result.name);
//    }
//
//    @Test
//    public void testNoNameInput() {
//      HelloWorldController hello = new HelloWorldController();
//      ModelAndView result = hello.showMessage("");
//      Assert.assertEquals("World", result.name);
//     }
// }

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
// @WebAppConfiguration
@SpringBootTest
public class PropertiesControllerTest {

	@Autowired
	private PropertiesController propertiesController;

	@InjectMocks
	PropertiesController controller;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testRoot() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void testGetIndex() throws Exception {
		this.mockMvc.perform(get("/index").accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
	}
	
	@Test
	public void testPropertiesMapSet() throws Exception {
		this.mockMvc.perform(get("/index").accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
		this.mockMvc.perform(get("/index").accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
	}
	
	@Test
	public void testPostIndex() throws Exception {
		this.mockMvc.perform(post("/index").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(model().attribute("firstName", "Unknown")).andExpect(model().attribute("lastName", "Unknown"));
		this.mockMvc.perform(post("/index").param("firstName", "testFirst").param("lastName", "testLast").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(model().attribute("firstName", "testFirst")).andExpect(model().attribute("lastName", "testLast"));
		this.mockMvc.perform(post("/index").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
				.andExpect(model().attribute("firstName", "testFirst")).andExpect(model().attribute("lastName", "testLast"));
	}
	
	@Test
	public void testPutProperty() throws Exception {
	    this.mockMvc.perform(put("/property").param("name", "testProperty").param("value", "testValue").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());;
	}
	
	@Test
	public void testGetProperties() throws Exception {
		this.mockMvc.perform(get("/properties.json").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void testSetProperty() throws Exception {
		this.mockMvc.perform(post("/property").param("name", "testProperty").param("value", "testValue").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
		this.mockMvc.perform(get("/property").param("name", "testProperty").param("value", "testValue").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void testPropertyVessel() throws Exception {
		PropertiesVessel pv = new PropertiesVessel();
		pv.setProperty("testName", "testValue");
		PropertiesVessel.Property p = pv.getProperty("testName");
		Assert.assertSame("testValue", p.getValue());
	}
	
	@Test
	public void testPropertyVesselProperty() throws Exception {
		PropertiesVessel.Property p1 = new PropertiesVessel.Property("testName", "testValue");
		PropertiesVessel.Property p2 = new PropertiesVessel.Property("testName", "testValue");
		PropertiesVessel.Property p3 = new PropertiesVessel.Property("testName2", "testValue");
		PropertiesVessel.Property p4 = new PropertiesVessel.Property("testName", "testValue2");
		
		Assert.assertEquals(p1, p2);
		Assert.assertNotSame(p1, p3);
		Assert.assertNotSame(p1, p4);
		Assert.assertEquals(p1.equals("string"), false);
		Assert.assertEquals(p1.compareTo(p2), 0);
		Assert.assertEquals(p1.compareTo(p3), -1);
		Assert.assertEquals(p3.compareTo(p1), 1);

	}
}
