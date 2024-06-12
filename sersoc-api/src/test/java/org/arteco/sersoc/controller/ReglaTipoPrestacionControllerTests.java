package org.arteco.sersoc.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.arteco.sersoc.dto.ReglaDTO;
import org.arteco.sersoc.model.entities.NoutPrestacions;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.service.DataService;
import org.arteco.sersoc.service.NashornService;
import org.arteco.sersoc.service.NoutPrestacionsService;
import org.arteco.sersoc.service.NoutReglesService;
import org.arteco.sersoc.service.NoutSQLStatementService;
import org.arteco.sersoc.service.NoutTipprsService;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ReglaTipoPrestacionController.class)
public class ReglaTipoPrestacionControllerTests {

	@MockBean
	private NoutTipprsService noutTipprsService;

	@MockBean
	private NoutReglesService service;

	@MockBean
	private ReglaTipoPrestacionService reglaTipoPrestacionService;

	@MockBean
	private NoutPrestacionsService noutPrestacionsService;

	@MockBean
	private NashornService nashornService;

	@MockBean
	private DataService dataService;

	@MockBean
	private NoutSQLStatementService noutSQLStatementService;

	@Autowired
	private MockMvc mockMvc;


	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void listAll_ShouldReturnListView() throws Exception {

		int page = 1;
		Pageable pageRequest = PageRequest.of(page, 2);
		List<ReglaTipoPrestacionEntity> reglaTipoPrestacionEntities = new ArrayList<>();
		reglaTipoPrestacionEntities.add(new ReglaTipoPrestacionEntity());

		NoutRegles regla = new NoutRegles();
		List<NoutRegles> regles = new ArrayList<>();
		regles.add(regla);
		Page<NoutRegles> reglesPage = new PageImpl<>(regles, pageRequest, regles.size());

		when(reglaTipoPrestacionService.findAll()).thenReturn(reglaTipoPrestacionEntities);
		when(service.findByActiveTrue(pageRequest)).thenReturn(reglesPage);

		mockMvc.perform(get("/regla-tipo-prestacion/list")
							.param("page", String.valueOf(page))
							.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("reglas/reglas"))
			.andExpect(model().attributeExists("reglas"))
			.andExpect(model().attributeExists("totalPages"))
			.andExpect(model().attributeExists("reglasTipoPrestacion"))
			.andExpect(model().attributeExists("currentPage"))
			.andExpect(model().attributeExists("titlePage"));

		verify(reglaTipoPrestacionService).findAll();
		verify(service).findByActiveTrue(pageRequest);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldShowEditarReglasPage() throws Exception {

		Long reglaId = 1L;

		NoutRegles regla = new NoutRegles();
		regla.setCon(reglaId);
		regla.setDec("description");
		regla.setDatIni(new Date());
		regla.setDatFin(new Date(System.currentTimeMillis() + 86400000L));
		regla.setScript("script content");

		List<NoutTipprs> allTipoPrestacion = new ArrayList<>();
		NoutTipprs tipoPrestacion = new NoutTipprs();
		allTipoPrestacion.add(tipoPrestacion);

		List<ReglaTipoPrestacionEntity> allReglasTipoPrestacion = new ArrayList<>();
		ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
		reglaTipoPrestacion.setNoutRegles(regla);
		reglaTipoPrestacion.setNoutTipprs(tipoPrestacion);
		reglaTipoPrestacion.setActive(true);
		allReglasTipoPrestacion.add(reglaTipoPrestacion);

		when(service.findById(reglaId)).thenReturn(java.util.Optional.of(regla));
		when(noutTipprsService.findAll()).thenReturn(allTipoPrestacion);
		when(reglaTipoPrestacionService.findAll()).thenReturn(allReglasTipoPrestacion);

		mockMvc.perform(get("/regla-tipo-prestacion/editar/1").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("reglas/editar_regla"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("reglaDTO"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("titlePage"));

		verify(service).findById(reglaId);
		verify(noutTipprsService).findAll();
		verify(reglaTipoPrestacionService).findAll();
	}

	@Test
	void shouldReturnForbiddenWhenUserIsNotAuthenticated() throws Exception {
		// Given
		Long reglaId = 1L;

		// When
		mockMvc.perform(get("/regla-tipo-prestacion/editar/{reglaId}", reglaId))
			.andExpect(status().isUnauthorized());

		// Then
		verifyNoInteractions(service, noutTipprsService, reglaTipoPrestacionService);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldReturnFormWithErrorsWhenBindingErrors() throws Exception {

		ReglaDTO reglaDTO = new ReglaDTO();
		reglaDTO.setCon(1L);
		reglaDTO.setDec("");
		reglaDTO.setDatIni(null);
		reglaDTO.setDatFin(null);
		reglaDTO.setScript("");

		List<NoutTipprs> allTipoPrestacion = new ArrayList<>();
		NoutTipprs tipoPrestacion = new NoutTipprs();
		allTipoPrestacion.add(tipoPrestacion);

		when(noutTipprsService.findAll()).thenReturn(allTipoPrestacion);

		mockMvc.perform(post("/regla-tipo-prestacion/save/1")
							.with(csrf())
							.param("id", reglaDTO.getCon().toString())
							.param("dec", reglaDTO.getDec())
							.param("datIni", reglaDTO.getDatIni() == null ? "" : reglaDTO.getDatIni().toString())
							.param("datFin", reglaDTO.getDatFin() == null ? "" : reglaDTO.getDatFin().toString())
							.param("script", reglaDTO.getScript())
							.param("tipoPrestacion", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("reglas/editar_regla"))
			.andExpect(model().attributeExists("reglaDTO"))
			.andExpect(model().attributeExists("titlePage"))
			.andExpect(model().attributeHasFieldErrors("reglaDTO", "datIni", "datFin"));

		verify(noutTipprsService).findAll();
		verifyNoInteractions(reglaTipoPrestacionService);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldDeleteRegla() throws Exception {

		Long reglaId = 1L;

		NoutRegles regla = new NoutRegles();
		regla.setCon(reglaId);

		when(service.findById(reglaId)).thenReturn(java.util.Optional.of(regla));

		mockMvc.perform(get("/regla-tipo-prestacion/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/regla-tipo-prestacion/list"));

		verify(service).delete(regla);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldShowCrearReglasPage() throws Exception {

		ReglaDTO reglaDTO = new ReglaDTO();
		reglaDTO.setAllTipoPrestacion(new ArrayList<>());
		Iterable<NoutTipprs> tipoPrestacionList = new ArrayList<>();

		when(noutTipprsService.findAll()).thenReturn(tipoPrestacionList);

		mockMvc.perform(get("/regla-tipo-prestacion/crear"))
			.andExpect(status().isOk())
			.andExpect(view().name("reglas/crear_regla"))
			.andExpect(model().attribute("reglaDTO", reglaDTO))
			.andExpect(model().attribute("tipoPrestacionList", tipoPrestacionList))
			.andExpect(model().attribute("titlePage", "Crear regla"));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "API", "USER"})
	void shouldRedirectFromSave() throws Exception {
		// Datos de prueba
		ReglaDTO reglaDTO = new ReglaDTO();
		reglaDTO.setCon(1L);
		reglaDTO.setDec("description");
		reglaDTO.setDatIni(new Date());
		reglaDTO.setDatFin(new Date(System.currentTimeMillis() + 86400000L));
		reglaDTO.setScript("script content");
		reglaDTO.setTipoPrestacionSelected(new ArrayList<>());
		reglaDTO.setAllTipoPrestacion(List.of(new NoutTipprs()));

		List<String> tipoPrestacionIds = Arrays.asList("1", "2");
		List<NoutTipprs> tipoPrestaciones = new ArrayList<>();

		// Simulación de servicios
		when(noutTipprsService.findAllById(tipoPrestacionIds)).thenReturn(tipoPrestaciones);

		// Realiza la solicitud POST
		mockMvc.perform(post("/regla-tipo-prestacion/save")
							.with(csrf())
							.flashAttr("reglaDTO", reglaDTO)
							.param("tipoPrestacion", String.join(",", tipoPrestacionIds)))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/regla-tipo-prestacion/list"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/regla-tipo-prestacion/list"));

		// Verificación de interacciones con los servicios
		verify(noutTipprsService).findAllById(tipoPrestacionIds);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldRedirectFromUpdate() throws Exception {

		Long reglaId = 1L;
		ReglaDTO reglaDTO = new ReglaDTO();
		// Set the required fields on the ReglaDTO object
		reglaDTO.setDatIni(new Date()); // Set the start date to the current date
		reglaDTO.setDatFin(new Date(System.currentTimeMillis() + 86400000L)); // Set the end date to 1 day in the future

		List<String> tipoPrestacionIds = Arrays.asList("1", "2");
		List<NoutTipprs> tipoPrestaciones = new ArrayList<>();

		// Simular recuperación de tipoPrestaciones por IDs y añadirla a tipoPrestaciones
		when(noutTipprsService.findAllById(tipoPrestacionIds)).thenReturn(tipoPrestaciones);

		NoutRegles regla = new NoutRegles();
		regla.setCon(reglaId);
		regla.setDec("description");
		regla.setDatIni(new Date());
		regla.setDatFin(new Date(System.currentTimeMillis() + 86400000L));
		regla.setScript("script content");

		//		 Realiza la solicitud POST con los parámetros correctos
		mockMvc.perform(post("/regla-tipo-prestacion/save/1")
							.with(csrf())
							.flashAttr("reglaDTO", reglaDTO)
							.param("tipoPrestacion", "1,2")) // IDs separados por comas
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(view().name("redirect:/regla-tipo-prestacion/list"));
	}

	//@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldShowValidatePage() throws Exception {
		// Arrange
		String prestacionTipoID = "IND";
		Long prestacionID = 1L;
		when(service.findByIdTipoPrestacion(prestacionTipoID)).thenReturn(new ArrayList<>());
		when(noutPrestacionsService.findById(prestacionID)).thenReturn(java.util.Optional.of(new NoutPrestacions()));

		// Act & Assert
		mockMvc.perform(get("/regla-tipo-prestacion/validation/{prestacionTipID}/{prestacionID}", prestacionTipoID, prestacionID)
							.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("reglas/validacion"))
			.andExpect(model().attributeExists("titlePage", "validationError", "validationWarning", "validationSuccess"));

		// Assert
		verify(service, times(1)).findByIdTipoPrestacion(prestacionTipoID);
		verify(noutPrestacionsService, times(1)).findById(prestacionID);
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void shouldShowPrestacion() throws Exception {
		// Crear datos de prueba
		Long prestacionId = 1L;
		NoutTipprs tipoPrestacion = new NoutTipprs();
		tipoPrestacion.setDec("Tipo de prestación");

		NoutPrestacions prestacion = new NoutPrestacions();
		prestacion.setCon(prestacionId);
		prestacion.setTipoPrestacion(tipoPrestacion);

		// Configurar comportamiento del servicio mock
		when(noutPrestacionsService.findById(prestacionId)).thenReturn(Optional.of(prestacion));

		// Realizar la solicitud GET al endpoint
		mockMvc.perform(get("/regla-tipo-prestacion/prestacions/{prestacionId}", prestacionId).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("reglas/prestacion"))
			.andExpect(model().attributeExists("tipoPrestacion"))
			.andExpect(model().attributeExists("prestacion"))
			.andExpect(model().attributeExists("titlePage"))
			.andExpect(model().attribute("titlePage", "Tipus de prestació "))
			.andExpect(model().attribute("prestacion", prestacion))
			.andExpect(model().attribute("tipoPrestacion", tipoPrestacion));
	}


}
