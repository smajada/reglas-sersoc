package org.arteco.sersoc.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class LoginControllerTests {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private Model model;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLogin() {

		String viewName = loginController.login(model);
		assert viewName.equals("autenticate/login");
		verify(model).addAttribute("titlePage", "Login");
	}

	@Test
	public void testIndex() {

		String viewName = loginController.index(model);
		assert viewName.equals("autenticate/home");
		verify(model).addAttribute("titlePage", "Home");
	}

	@Test
	public void testAccessDenied() {

		String viewName = loginController.accessDenied(model);
		assert viewName.equals("error/access-denied");
		verify(model).addAttribute("titlePage", "Access Denied");
	}

	@Test
	public void testNotFound() {

		String viewName = loginController.notFound(model);
		assert viewName.equals("error/not-found");
		verify(model).addAttribute("titlePage", "Not-found");
	}
}
