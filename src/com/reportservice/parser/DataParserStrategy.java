package com.reportservice.parser;

import java.util.Locale;
import java.util.Scanner;

import com.reportservice.exception.ParserException;

public abstract class DataParserStrategy {
	
	public static final String DELIMITER = "ç";
	
    public abstract void parserAndSave(final String toBeParsed) throws ParserException;

    protected Scanner createScanner(final String toBeParsed, final String delimiter) {
    	Scanner scanner = new Scanner(toBeParsed);
    	scanner.useDelimiter(delimiter);
    	scanner.useLocale(Locale.ENGLISH);
    	return scanner;
    }
    
    protected Scanner createScanner(final String toBeParsed) {
    	return this.createScanner(toBeParsed, DELIMITER);
    }
}
