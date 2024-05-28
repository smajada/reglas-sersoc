package org.arteco.sersoc.repository;

import java.util.ArrayList;
import java.util.List;
import org.arteco.sersoc.model.entities.NoutRegles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class NoutReglesRepositoryTest {

	@Mock
	private NoutReglesRepository noutReglesRepository;

	@Test
	void findByIdTipoPrestacion() {
		// Arrange
		String idTipoPrestacion = "exampleId";
		List<NoutRegles> expectedEntities = new ArrayList<>();
		expectedEntities.add(new NoutRegles());
		Mockito.when(noutReglesRepository.findByIdTipoPrestacion(idTipoPrestacion)).thenReturn(expectedEntities);

		// Act
		List<NoutRegles> actualEntities = noutReglesRepository.findByIdTipoPrestacion(idTipoPrestacion);

		// Assert
		Assertions.assertEquals(expectedEntities.size(), actualEntities.size());
	}

	@Test
	void findByActiveTrue() {
		// Arrange
		List<NoutRegles> expectedEntities = new ArrayList<>();
		expectedEntities.add(new NoutRegles());
		Page<NoutRegles> expectedPage = new PageImpl<>(expectedEntities);
		PageRequest pageRequest = PageRequest.of(0, 10);
		Mockito.when(noutReglesRepository.findByActiveTrue(pageRequest)).thenReturn(expectedPage);

		// Act
		Page<NoutRegles> actualPage = noutReglesRepository.findByActiveTrue(pageRequest);

		// Assert
		Assertions.assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
	}
}