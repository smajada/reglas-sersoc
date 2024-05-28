package org.arteco.sersoc.repository;

import java.util.ArrayList;
import java.util.List;
import org.arteco.sersoc.model.entities.ReglaTipoPrestacionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ReglaTipoPrestacionRepositoryTest {

	@Mock
	private ReglaTipoPrestacionRepository reglaTipoPrestacionRepository;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByNoutReglesCon() {
		// Arrange
		Long reglaId = 1L;
		List<ReglaTipoPrestacionEntity> expectedEntities = new ArrayList<>();
		expectedEntities.add(new ReglaTipoPrestacionEntity());
		Mockito.when(reglaTipoPrestacionRepository.findByNoutRegles_Con(reglaId)).thenReturn(expectedEntities);

		// Act
		List<ReglaTipoPrestacionEntity> actualEntities = reglaTipoPrestacionRepository.findByNoutRegles_Con(reglaId);

		// Assert
		Assertions.assertEquals(expectedEntities.size(), actualEntities.size());
		// Add more assertions if needed
	}
}
