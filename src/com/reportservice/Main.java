package com.reportservice;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;

import com.reportservice.controller.ReportController;

public class Main {

	public static void main(String[] args) {
		final String homePath = System.getenv("HOMEPATH");

		if( homePath != null && !homePath.isEmpty() && Files.isDirectory(Paths.get(homePath))) {
			final ReportController controller = new ReportController(homePath);
	
			final Timer t = new Timer();
			t.scheduleAtFixedRate(controller, 0, 10000);
		} else {
			System.out.println("HOME-PATH variable is invalid or wasnt setted. Finishing proccess.");
		}
	}
}
