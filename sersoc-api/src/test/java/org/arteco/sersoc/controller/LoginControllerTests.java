package org.arteco.sersoc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(LoginController.class)
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void LoginController_publicPage_ReturnRedirect() throws Exception {

		mockMvc.perform(get("/public"))
			.andExpect(status().isOk())
			.andExpect(view().name("autenticate/prueba"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void loginController_authenticatedUser_ReturnRedirect() throws Exception {

		// Perform the request and capture the result
		MvcResult result = mockMvc.perform(get("/login"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/home"))
			.andReturn();

		// Log the response for debugging purposes
		System.out.println("Response: " + result.getResponse().getContentAsString());
	}

	@Test
	void loginController_unauthenticatedUser_ReturnLoginView() throws Exception {

		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("autenticate/login"))
			.andExpect(model().attributeExists("titlePage"))
			.andExpect(model().attribute("titlePage", "Login"))
			.andExpect(model().attributeExists("username"));
	}
}
