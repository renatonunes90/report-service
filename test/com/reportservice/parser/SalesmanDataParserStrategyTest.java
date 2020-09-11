package com.reportservice.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.builder.SalesmanBuilder;
import com.reportservice.entity.Salesman;
import com.reportservice.exception.ParserException;
import com.reportservice.store.SalesmanStore;
import com.reportservice.util.ErrorMessage;

@ExtendWith(MockitoExtension.class)
public class SalesmanDataParserStrategyTest {

	private SalesmanDataParserStrategy component = new SalesmanDataParserStrategy();
	
	@Mock
	private SalesmanStore store;
	
	@BeforeEach
	public void setup(){
		component = new SalesmanDataParserStrategy(store);
	}
	
	@Test
	public void shouldSaveNewSalesman() throws ParserException {
		final Salesman newItem = SalesmanBuilder.defaults();
				
		component.parserAndSave( String.format("%sç%sç%s", newItem.getCpf(), newItem.getName(), newItem.getSalary()));
		
    	verify(store).addItem(newItem);
	}
	
	@Test
	public void shouldThrowExceptionMissInformation() throws ParserException {
		assertException("ççç", ErrorMessage.SALESMAN_MISSING_INFO);
		assertException("çnameç", ErrorMessage.SALESMAN_MISSING_INFO);
		assertException("   ç   ç     ", ErrorMessage.SALESMAN_MISSING_INFO);
		assertException("cpfçnameçstringSalary", ErrorMessage.SALESMAN_MISSING_INFO);
	}
	
	@Test
	public void shouldThrowExceptionEmptyString() throws ParserException {
		assertException(null, ErrorMessage.SALESMAN_EMPTY_LINE);
		assertException("", ErrorMessage.SALESMAN_EMPTY_LINE);
		assertException("      ", ErrorMessage.SALESMAN_EMPTY_LINE);
	}
	
	private void assertException(final String toBeParsed, final ErrorMessage expected) {
		final Exception exception = assertThrows(ParserException.class, () ->
			component.parserAndSave(toBeParsed));
		assertEquals(expected.getMessage(), exception.getMessage());
	}
	
}
