package org.arteco.sersoc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.arteco.sersoc.dto.PageDTO;
import org.arteco.sersoc.model.entities.NoutSQLStatement;
import org.arteco.sersoc.repository.NoutSQLStatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class NoutSQLStatementServiceTests {

	@Mock
	private NoutSQLStatementRepository repo;

	@InjectMocks
	private NoutSQLStatementService service;

	@Test
	void NoutSQLStatementService_update_ReturnRTP() {

		Long aLong = 1L;
		NoutSQLStatement bean = new NoutSQLStatement();

		service.update(bean, aLong);

		verify(repo).save(any());
	}

	@Test
	void NoutSQLStatementService_delete_ReturnRTP() {

		NoutSQLStatement bean = new NoutSQLStatement();

		service.delete(bean);

		verify(repo).save(any());
	}

	@Test
	void NoutSQLStatementService_findByActiveTrue_ReturnRTP() {

		NoutSQLStatement statement = new NoutSQLStatement();
		List<NoutSQLStatement> statementList = Collections.singletonList(statement);
		Page<NoutSQLStatement> page = new PageImpl<>(statementList);
		Pageable pageable = PageRequest.of(0, 10);

		when(repo.findByActiveTrue(pageable)).thenReturn(page);

		PageDTO<NoutSQLStatement> result = service.findByActiveTrue(pageable);

		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		assertEquals(statementList.get(0), result.getContent().get(0));

		verify(repo, times(1)).findByActiveTrue(pageable);
	}

	@Test
	void NoutSQLStatementService_getAllSqlStatement_ReturnRTP() {

		NoutSQLStatement statement1 = new NoutSQLStatement(1L, "key1", "description", "value1", true);
		NoutSQLStatement statement2 = new NoutSQLStatement(2L, "key2", "description", "value2", true);
		List<NoutSQLStatement> statementList = Arrays.asList(statement1, statement2);
		when(repo.findAll()).thenReturn(statementList);

		Map<String, String> result = service.getAllSqlStatement();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("value1", result.get("key1"));
		assertEquals("value2", result.get("key2"));

		verify(repo, times(1)).findAll();
	}

	@Test
	void NoutSQLStatementService_getAllSqlStatementByActive_ReturnRTP() {

		NoutSQLStatement statement1 = new NoutSQLStatement(1L, "key1", "description", "value1", true);
		NoutSQLStatement statement2 = new NoutSQLStatement(2L, "key2", "description", "value2", true);
		List<NoutSQLStatement> statementList = Arrays.asList(statement1, statement2);
		when(repo.findByActiveTrue()).thenReturn(statementList);

		Map<String, String> result = service.getAllSqlStatementByActive();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("value1", result.get("key1"));
		assertEquals("value2", result.get("key2"));

		verify(repo, times(1)).findByActiveTrue();
	}

}
