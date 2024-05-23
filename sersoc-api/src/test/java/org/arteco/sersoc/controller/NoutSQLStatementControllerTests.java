package org.arteco.sersoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.service.NoutSQLStatementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

@WebMvcTest(NoutSQLStatementController.class)
public class NoutSQLStatementControllerTests {

	@MockBean
	private NoutSQLStatementService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_listAll_ReturnRedirect() throws Exception {

		Pageable pageRequest = PageRequest.of(0, 10);

		when(service.findByActiveTrue(pageRequest)).thenReturn(new PageDTO<>(new PageImpl<>(new ArrayList<>())));

		mockMvc.perform(MockMvcRequestBuilders.get("/sql/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("sql/sql_statement"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_createSqlSentence_ReturnRedirect() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/sql/crear"))
			.andExpect(status().isOk())
			.andExpect(view().name("sql/crear_sentencia"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_saveSQLStatement_NoErrors_ReturnRedirect() throws Exception {

		NoutSQLStatement sql = new NoutSQLStatement(1L, "key", "description", "value", true);

		// Mocking the binding result to simulate no validation errors
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		when(bindingResult.hasErrors()).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.post("/sql/save")
							.flashAttr("sql", sql)
							.flashAttr("org.springframework.validation.BindingResult.sql", bindingResult)
							.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/sql/list"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_editSqlSentence_ReturnRedirect() throws Exception {

		Long sqlId = 1L;

		NoutSQLStatement sql = new NoutSQLStatement(1L, "key", "description", "value", true);

		when(service.findById(sqlId)).thenReturn(java.util.Optional.of(sql));

		mockMvc.perform(MockMvcRequestBuilders.get("/sql/editar/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("sql/editar_sentencia"));
	}

	//	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_updateSQLStatement_NoErrors_ReturnRedirect() throws Exception {

		Long con = 1L;

		NoutSQLStatement sql = new NoutSQLStatement(1L, "key", "description", "value", true);

		// Mocking the binding result to simulate no validation errors
		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		when(bindingResult.hasErrors()).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.post("/sql/save/1")
							.flashAttr("sql", sql)
							.flashAttr("org.springframework.validation.BindingResult.sql", bindingResult)
							.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/sql/list"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void NoutSQLStatementController_deleteSQLStatement_ReturnRedirect() throws Exception {

		Long con = 1L;

		NoutSQLStatement sql = new NoutSQLStatement(1L, "key", "description", "value", true);

		when(service.findById(con)).thenReturn(java.util.Optional.of(sql));

		mockMvc.perform(MockMvcRequestBuilders.get("/sql/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/sql/list"));
	}

}
