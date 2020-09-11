package com.reportservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.builder.ReportBuilder;
import com.reportservice.entity.Report;
import com.reportservice.service.FileService;
import com.reportservice.service.ImportService;
import com.reportservice.service.ReportService;
import com.reportservice.store.ClientStore;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

	private ReportController component;
	
	@Mock
	private FileService fileService;
	
	@Mock
	private ImportService importer;
	
	@Mock
	private ReportService reporter;	
	
	@Mock
	private ClientStore clientStore;
	
	@Mock
	private SalesmanStore salesmanStore;
	
	@Mock
	private SaleStore saleStore;
	
	@BeforeEach
	public void setup(){
		component = new ReportController(fileService, importer, reporter, clientStore, salesmanStore, saleStore);
	}
	
	@Test
	public void shouldDoesAllOpertionsForEachFile() {
		final String fullPath = "fullPath";
		final List<String> files = Arrays.asList("fileA.txt", "fileB.txt");
		final Report report = ReportBuilder.defaults();
		
		when(fileService.extractInputFiles()).thenReturn(files);
		when(fileService.getInputFileFullPath(anyString())).thenReturn(fullPath);
		when(reporter.generateReport()).thenReturn(report);
		
		component.run();
		
		verify(importer, times(2)).loadDataFromFile(fullPath);
		verify(reporter, times(2)).generateReport();
		verify(fileService).printReport(report, files.get(0));
		verify(fileService).printReport(report, files.get(1));
		verify(fileService, times(2)).removeParsedFile(fullPath);
	}
	
	@Test
	public void shouldDoesNothingWhenHasntFile() {
		final List<String> files = Arrays.asList();
		
		when(fileService.extractInputFiles()).thenReturn(files);
		
		component.run();
		
		verify(importer, never()).loadDataFromFile(anyString());
		verify(reporter,never()).generateReport();
		verify(fileService, never()).printReport(any(), anyString());
		verify(fileService, never()).removeParsedFile(anyString());
	}
}
