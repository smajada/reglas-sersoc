package org.arteco.sersoc.repository;

import java.util.ArrayList;
import java.util.List;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class NoutSQLStatementRepositoryTest {

	@Mock
	private NoutSQLStatementRepository noutSQLStatementRepository;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);
	}


	@Test
	void findByActiveTrue() {

		// Arrange
		List<NoutSQLStatement> expectedEntities = new ArrayList<>();
		expectedEntities.add(new NoutSQLStatement());
		Mockito.when(noutSQLStatementRepository.findByActiveTrue()).thenReturn(expectedEntities);

		// Act
		List<NoutSQLStatement> actualEntities = noutSQLStatementRepository.findByActiveTrue();

		// Assert
		Assertions.assertEquals(expectedEntities.size(), actualEntities.size());


	}

	@Test
	void testFindByActiveTrue() {
		// Arrange
		List<NoutSQLStatement> expectedEntities = new ArrayList<>();
		expectedEntities.add(new NoutSQLStatement());
		Page<NoutSQLStatement> expectedPage = new PageImpl<>(expectedEntities);
		PageRequest pageRequest = PageRequest.of(0, 10);
		Mockito.when(noutSQLStatementRepository.findByActiveTrue(pageRequest)).thenReturn(expectedPage);

		// Act
		Page<NoutSQLStatement> actualPage = noutSQLStatementRepository.findByActiveTrue(pageRequest);

		// Assert
		Assertions.assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
	}
}