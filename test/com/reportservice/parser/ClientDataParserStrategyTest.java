package com.reportservice.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.builder.ClientBuilder;
import com.reportservice.entity.Client;
import com.reportservice.exception.ParserException;
import com.reportservice.store.ClientStore;
import com.reportservice.util.ErrorMessage;

@ExtendWith(MockitoExtension.class)
public class ClientDataParserStrategyTest {

	private ClientDataParserStrategy component = new ClientDataParserStrategy();
	
	@Mock
	private ClientStore store;
	
	@BeforeEach
	public void setup(){
		component = new ClientDataParserStrategy(store);
	}
	
	@Test
	public void shouldSaveNewClient() throws ParserException {
		final Client newItem = ClientBuilder.defaults();
				
		component.parserAndSave( String.format("%sç%sç%s", newItem.getCnpj(), newItem.getName(), newItem.getBusinessArea()));
		
    	verify(store).addItem(newItem);
	}
	
	@Test
	public void shouldThrowExceptionMissInformation() throws ParserException {
		assertException("ççç", ErrorMessage.CLIENT_MISSING_INFO);
		assertException("çnameç", ErrorMessage.CLIENT_MISSING_INFO);
		assertException("   ç   ç     ", ErrorMessage.CLIENT_MISSING_INFO);
	}
	
	@Test
	public void shouldThrowExceptionEmptyString() throws ParserException {
		assertException(null, ErrorMessage.CLIENT_EMPTY_LINE);
		assertException("", ErrorMessage.CLIENT_EMPTY_LINE);
		assertException("      ", ErrorMessage.CLIENT_EMPTY_LINE);
	}
	
	private void assertException(final String toBeParsed, final ErrorMessage expected) {
		final Exception exception = assertThrows(ParserException.class, () ->
			component.parserAndSave(toBeParsed));
		assertEquals(expected.getMessage(), exception.getMessage());
	}
	
}
