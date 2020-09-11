package com.reportservice.controller;

import java.util.List;
import java.util.TimerTask;

import com.google.common.annotations.VisibleForTesting;
import com.reportservice.service.FileService;
import com.reportservice.service.ImportService;
import com.reportservice.service.ReportService;
import com.reportservice.store.ClientStore;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;

public class ReportController extends TimerTask {

	private FileService fileService;
	private ImportService importer;
	private ReportService reporter;

	private ClientStore clientStore;
	private SalesmanStore salesmanStore;
	private SaleStore saleStore;
	
	public ReportController(final String basePath) {
		clientStore = ClientStore.getInstance();
		salesmanStore = SalesmanStore.getInstance();
		saleStore = SaleStore.getInstance();
		
		fileService = new FileService(basePath);
		importer = new ImportService(fileService);
		reporter = new ReportService();
	}
	
	@VisibleForTesting
	public ReportController(final FileService fileService, final ImportService importService,
			final ReportService reportService, final ClientStore clientStore, final SalesmanStore salesmanStore,
			final SaleStore saleStore) {
		this.fileService = fileService;
		this.importer = importService;
		this.reporter = reportService;
		
		this.clientStore = clientStore;
		this.salesmanStore = salesmanStore;
		this.saleStore = saleStore;
	}
	
	@Override
	public void run() {
        final List<String> files = fileService.extractInputFiles();
        files.stream().forEach(file -> {
    		clientStore.clear();
    		salesmanStore.clear();
    		saleStore.clear();
    		
        	importer.loadDataFromFile(fileService.getInputFileFullPath(file));
        	fileService.printReport(reporter.generateReport(), file);
        	fileService.removeParsedFile(fileService.getInputFileFullPath(file));
        });
	}

	
}
