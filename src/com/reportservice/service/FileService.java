package com.reportservice.service;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.reportservice.entity.Report;
import com.reportservice.util.ErrorMessage;

public class FileService {

	private File directoryInPath;
	private File directoryOutPath;
	
	public FileService(final String filePath) {
		final String baseFolderPath = filePath + "\\data";
		File baseFolder = new File(baseFolderPath);
		directoryInPath = new File(baseFolderPath + "\\in");
		directoryOutPath = new File(baseFolderPath + "\\out");
		
		createFolderIfNotExists(baseFolder);
		createFolderIfNotExists(directoryInPath);
		createFolderIfNotExists(directoryOutPath);
	}
	
	public List<String> extractInputFiles() {
		FilenameFilter textFilefilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".txt")) {
					return true;
				} else {
					return false;
				}
			}
		};

		return Arrays.asList(directoryInPath.list(textFilefilter));
	}
	
	public String getInputFileFullPath(final String filename) {
		return String.format("%s\\%s", directoryInPath.getAbsolutePath(), filename);
	}
	
	public void printReport( final Report report, final String fileName ) {
		if ( report.isValid() ) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
			final Date date = new Date();		
			final File resultFile = new File(String.format("%s\\%s-%s", directoryOutPath.getAbsolutePath(), sdf.format(date), fileName));
			try {
				if ( resultFile.createNewFile() ) {
					writeOnFile(resultFile, report.mount());
				}
			} catch (IOException e) {
				printLog( String.format(ErrorMessage.ERROR_CREATING_REPORT_FILE.getMessage(), fileName));
			}
		}
	}
	
	public void printLog(final String message) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Date date = new Date();		
		final File logFile = new File(String.format("%s\\%s", directoryOutPath.getAbsolutePath(), "log.txt"));
		try {
			final String msgWithDate = String.format("%s: %s\n",sdf.format(date), message);
			writeOnFile(logFile, msgWithDate);
		} catch (IOException e) {
			
		}
	}
	
	public void removeParsedFile(final String filename) {
		final File resultFile = new File(filename);
		resultFile.delete();
	}
	
	private void createFolderIfNotExists(final File file) {
		if ( !file.exists() ) {
			file.mkdir();
		}
	}
	
	private void writeOnFile(final File file, final String content) throws IOException {
		final FileWriter writter = new FileWriter(file, true);
		writter.append(content);
		writter.close();
	}
}
