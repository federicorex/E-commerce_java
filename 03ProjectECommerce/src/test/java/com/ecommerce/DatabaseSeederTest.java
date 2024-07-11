package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;

@ExtendWith(MockitoExtension.class)
public class DatabaseSeederTest {

	@InjectMocks
	private DatabaseSeeder databaseSeeder;
	
	@Mock
	UserDAORepository userDAORepository;
	
	@Mock
	ProductDAORepository productDAORepository;
	
	@Mock
	OrderDAORepository orderDAORepository;
	
	void setUp() {
		MockitoAnnotations.openMocks(databaseSeeder);
	}
	
	@Test
	void testRunProductDAOCount1() throws Exception {
		String[] stringArgs = {"args"};
		
		lenient().when(productDAORepository.count()).thenReturn(1L);
		
		assertDoesNotThrow(() -> databaseSeeder.run(stringArgs));
	}
	
	@Test
	void testRunUserDAOCount1() throws Exception {
		String[] stringArgs = {"args"};
		
		lenient().when(userDAORepository.count()).thenReturn(1L);
		
		assertDoesNotThrow(() -> databaseSeeder.run(stringArgs));
	}
	
	@Test
	void testRunUserDAOAndProductDAOCount1() throws Exception {
		String[] stringArgs = {"args"};
		
		lenient().when(userDAORepository.count()).thenReturn(1L);
		lenient().when(productDAORepository.count()).thenReturn(1L);
		
		assertDoesNotThrow(() -> databaseSeeder.run(stringArgs));
	}
	
	@Test
	void testRun() throws Exception {
		String[] stringArgs = {"args"};
		
		assertDoesNotThrow(() -> databaseSeeder.run(stringArgs));
	}
}
