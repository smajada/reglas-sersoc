package org.arteco.sersoc.controller;


import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.service.NoutReglesService;
import org.arteco.sersoc.service.NoutTipprsService;
import org.arteco.sersoc.service.ReglaTipoPrestacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@WebMvcTest(ReglaTipoPrestacionController.class)
public class ReglaTipoPrestacionControllerTests {

    @MockBean
    private NoutTipprsService noutTipprsService;

    @MockBean
    private NoutReglesService noutReglesService;

    @MockBean
    private NoutReglesController noutReglesController;

    @MockBean
    private ReglaTipoPrestacionService service;


    @Autowired
    private MockMvc mockMvc;


    @Test
    void ReglasTipoPrestacionController_listAll_ReturnRedirect() throws Exception {
        Pageable pageRequest = PageRequest.of(0, 20);

        List<NoutRegles> regles = new ArrayList<>();
        List<ReglaTipoPrestacionEntity> reglasTipoPrestacion = new ArrayList<>();

        PageDTO<NoutRegles> reglesPage = new PageDTO<>(new PageImpl<>(regles));
        PageDTO<ReglaTipoPrestacionEntity> reglasTipoPrestacionPage = new PageDTO<>(new PageImpl<>(reglasTipoPrestacion));

        when(service.page(pageRequest)).thenReturn(new PageImpl<>(reglasTipoPrestacion));
        when(noutReglesController.page(pageRequest)).thenReturn(reglesPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/regla-tipo-prestacion/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("reglas/reglas"))
                .andExpect(MockMvcResultMatchers.model().attribute("totalPages", reglesPage.getTotalPages()))
                .andExpect(MockMvcResultMatchers.model().attribute("reglasTipoPrestacion", reglasTipoPrestacionPage.getContent()))
                .andExpect(MockMvcResultMatchers.model().attribute("reglas", reglesPage.getContent()))
                .andExpect(MockMvcResultMatchers.model().attribute("currentPage", 0))
                .andExpect(MockMvcResultMatchers.model().attribute("titlePage", "Reglas "));

    }

    @Test
    void shouldShowEditarReglasPage() throws Exception {
        Long reglaId = 1L;

        NoutRegles regla = new NoutRegles();
        regla.setCon(1L);

        List<NoutTipprs> allTipoPrestacion = new ArrayList<>();
        List<ReglaTipoPrestacionEntity> allReglasTipoPrestacion = new ArrayList<>();

        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();

        when(noutReglesService.findById(reglaId)).thenReturn(java.util.Optional.of(regla));
        when(noutTipprsService.findAll()).thenReturn(allTipoPrestacion);
        when(service.findAll()).thenReturn(allReglasTipoPrestacion);

        mockMvc.perform(MockMvcRequestBuilders.get("/regla-tipo-prestacion/editar/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("reglas/editar_regla"))
                .andExpect(MockMvcResultMatchers.model().attribute("reglaTipoPrestacion", reglaTipoPrestacion))
                .andExpect(MockMvcResultMatchers.model().attribute("regla", regla))
                .andExpect(MockMvcResultMatchers.model().attribute("allTipoPrestacion", allTipoPrestacion))
                .andExpect(MockMvcResultMatchers.model().attribute("reglasTipoPrestacionId", new ArrayList<>()))
                .andExpect(MockMvcResultMatchers.model().attribute("titlePage", "Editar regla")
                );

    }

    @Test
    void shouldDeleteRegla() throws Exception {
        Long reglaId = 1L;

        NoutRegles regla = new NoutRegles();
        regla.setCon(reglaId);

        when(noutReglesService.findById(reglaId)).thenReturn(java.util.Optional.of(regla));

        mockMvc.perform(MockMvcRequestBuilders.get("/regla-tipo-prestacion/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/regla-tipo-prestacion/list"));

        verify(noutReglesService, times(1)).delete(regla);
    }

    @Test
    void shouldShowCrearReglasPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/regla-tipo-prestacion/crear"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("reglas/crear_regla"))
                .andExpect(MockMvcResultMatchers.model().attribute("regla", new NoutRegles()))
                .andExpect(MockMvcResultMatchers.model().attribute("tipoPrestaciones", noutTipprsService.findAll()));
    }

    @Test
    void shouldRedirectFromSave() throws Exception {
        NoutRegles regla = new NoutRegles();
        regla.setCon(1L);

        // Simula la lista de tipo de prestaciones devuelta por el servicio
        List<NoutTipprs> tipoPrestaciones = new ArrayList<>();
        tipoPrestaciones.add(new NoutTipprs());

        // Configura el comportamiento esperado del servicio
        when(noutTipprsService.findAllById(anyList())).thenReturn(tipoPrestaciones);

        // Realiza la solicitud POST con los parámetros correctos
        mockMvc.perform(MockMvcRequestBuilders.post("/regla-tipo-prestacion/save")
                        .flashAttr("regla", regla)
                        .param("tipoPrestacion", String.valueOf(tipoPrestaciones)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/regla-tipo-prestacion/list"));

    }

    @Test
    void shouldRedirectFromUpdate() throws Exception {
        Long reglaId = 1L;
        NoutRegles regla = new NoutRegles();
        regla.setCon(reglaId);

        // Simula la lista de tipo de prestaciones devuelta por el servicio
        List<NoutTipprs> tipoPrestaciones = new ArrayList<>();
        tipoPrestaciones.add(new NoutTipprs());

        // Configura el comportamiento esperado del servicio
        when(noutTipprsService.findAllById(anyList())).thenReturn(tipoPrestaciones);

        // Realiza la solicitud POST con los parámetros correctos
        mockMvc.perform(MockMvcRequestBuilders.post("/regla-tipo-prestacion/save/1")
                        .flashAttr("regla", regla)
                        .param("tipoPrestacion", String.valueOf(tipoPrestaciones)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/regla-tipo-prestacion/list"));

    }

}
