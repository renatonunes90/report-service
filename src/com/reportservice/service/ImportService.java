package com.reportservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.reportservice.exception.ParserException;
import com.reportservice.parser.DataParserStrategy;
import com.reportservice.parser.DataParserStrategyType;
import com.reportservice.util.DataType;
import com.reportservice.util.ErrorMessage;

public class ImportService {

	private FileService fileService;
	
	public ImportService(final FileService fileService) {
		this.fileService = fileService;
	}
	
	public void loadDataFromFile(final String file) {	
	    final List<String> lines = loadFile(file);
	    lines.stream().forEach( line -> {
	    	if ( !line.isEmpty() && line.indexOf(DataParserStrategy.DELIMITER) > 0 ) {
	    		final String code = line.substring(0, line.indexOf(DataParserStrategy.DELIMITER));
	    		if ( !code.isEmpty() ) {
		    		DataType dataType = getDataType(code);
		    		if ( dataType != null ) {
		    			try {
		    				DataParserStrategyType.get(dataType).parserAndSave(line.substring(line.indexOf(DataParserStrategy.DELIMITER)));
		    			} catch ( ParserException ex ) {
		    				fileService.printLog(ex.getMessage());	    				
		    			}
		    		}
		    	}
	    	} else {
	    		fileService.printLog(String.format(ErrorMessage.INVALID_DATA_FORMAT.getMessage(), file));
	    	}
	    });  
	}
	
	private DataType getDataType(final String value) {
   	    try {
			return DataType.fromSigla(value);
		} catch (IllegalArgumentException e) {
			fileService.printLog(e.getMessage());
			return null;
		}
	}
	
	private List<String> loadFile(final String pathToFile) {
		final Path path = Paths.get(pathToFile);
   	    try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			fileService.printLog(String.format(ErrorMessage.ERROR_LOADING_INPUT_FILE.getMessage(), pathToFile));
			return Arrays.asList();
		}
	}
}
