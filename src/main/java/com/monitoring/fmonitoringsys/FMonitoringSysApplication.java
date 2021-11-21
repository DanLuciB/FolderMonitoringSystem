package com.monitoring.fmonitoringsys;

import com.monitoring.fmonitoringsys.service.FMonitoringService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FMonitoringSysApplication {
	public static void main(String[] args) {
		// Disable spring restart whenever files on the classpath change.
		System.setProperty("spring.devtools.restart.enabled", "false");
		FMonitoringService service = new FMonitoringService();
		service.startFilesNewMonitoring();
		SpringApplication.run(FMonitoringSysApplication.class, args);
	}
}
