package org.arteco.sersoc.service;

import org.arteco.sersoc.model.base.ReglasTipoPrestacionId;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.arteco.sersoc.repository.NoutReglesRepository;
import org.arteco.sersoc.repository.ReglaTipoPrestacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglaTipoPrestacionServiceTest {

    @Mock
    private ReglaTipoPrestacionRepository reglaTipoPrestacionRepository;

    @Mock
    private NoutReglesRepository noutReglesRepository;

    @InjectMocks
    private ReglaTipoPrestacionService reglaTipoPrestacionService;

    @Test
    public void ReglaTipoPrestacionService_save_ReturnRTP(){
        NoutRegles regla = new NoutRegles();
        List<NoutTipprs> tiposPrestacion = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            NoutTipprs tipoPrestacion = new NoutTipprs();
            tipoPrestacion.setCoa(String.valueOf(i));
            tiposPrestacion.add(tipoPrestacion);
        }

        when(noutReglesRepository.save(any())).thenReturn(regla);

        reglaTipoPrestacionService.save(regla, tiposPrestacion);

        verify(noutReglesRepository, times(1)).save(any());
        verify(reglaTipoPrestacionRepository, times(5)).save(any());
    }

    @Test
    public void ReglaTipoPrestacionService_updateAll_ReturnRTP(){
        NoutRegles regla = new NoutRegles();
        List<NoutTipprs> tiposPrestacion = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            NoutTipprs tipoPrestacion = new NoutTipprs();
            tipoPrestacion.setCoa(String.valueOf(i));
            tiposPrestacion.add(tipoPrestacion);
        }

        when(noutReglesRepository.save(any())).thenReturn(regla);

        reglaTipoPrestacionService.updateAll(1L, regla, tiposPrestacion);

        verify(noutReglesRepository, times(1)).save(any());
        verify(reglaTipoPrestacionRepository, times(5)).save(any());
    }

    @Test
    public void ReglaTipoPrestacionService_delete_ReturnFalse(){
        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();

        reglaTipoPrestacionService.delete(reglaTipoPrestacion);

        assertFalse(reglaTipoPrestacion.getActive());
    }

    @Test
    public void ReglaTipoPrestacionService_update_ReturnRTP(){
        ReglaTipoPrestacionEntity reglaTipoPrestacion = new ReglaTipoPrestacionEntity();
        ReglasTipoPrestacionId reglasTipoPrestacionId = new ReglasTipoPrestacionId(1L, "1");

        when(reglaTipoPrestacionRepository.findById(any())).thenReturn(java.util.Optional.of(reglaTipoPrestacion));

        reglaTipoPrestacionService.update(reglaTipoPrestacion, reglasTipoPrestacionId);

        verify(reglaTipoPrestacionRepository, times(1)).save(any());
    }


}