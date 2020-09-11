package com.reportservice.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.util.DataType;
import com.reportservice.util.ErrorMessage;

@ExtendWith(MockitoExtension.class)
public class ImportServiceTest {

	private ImportService component;
	
	private String basePath = "test//resources//";
	
	@Mock
	private FileService fileService;
	
	@BeforeEach
	public void setup(){
		component = new ImportService(fileService);
	}
	
	@Test
	public void shouldImportFile() {
		component.loadDataFromFile(basePath + "test.txt");	

		verify(fileService, never()).printLog(anyString());
	}
	
	@Test
	public void shouldImportEmptyFileThrowsError() {
		final String filename = basePath + "testEmpty.txt";
		component.loadDataFromFile(filename);	

		verify(fileService).printLog(String.format(ErrorMessage.INVALID_DATA_FORMAT.getMessage(), filename));
	}
	
	@Test
	public void shouldImportThrowsInvalidaDataTypeError() {
		final String filename = basePath + "testInvalidDataType.txt";
		component.loadDataFromFile(filename);	

		verify(fileService).printLog(String.format(ErrorMessage.INVALID_DATA_TYPE.getMessage(), "004", Arrays.toString(DataType.values())));
	}
	
}
