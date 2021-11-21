package com.monitoring.fmonitoringsys.web.rest;

import java.time.LocalDateTime;

import com.monitoring.fmonitoringsys.service.FMonitoringService;
import com.monitoring.fmonitoringsys.service.IFileMonitoring;
import com.monitoring.fmonitoringsys.to.ResultTO;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * REST Controller che espone i due servizi rest per ottenere informazioni dei
 * file della cartella di monitoring, dati alcuni parametri in ingresso
 * 
 * @author dlucibello
 */
@RestController
@RequestMapping("/fmonitoringsys/api")
public class FMonitoringSysController {
	private IFileMonitoring service;
	public FMonitoringSysController(){
		this.service = new FMonitoringService();
	}

	/**
	 * {@code GET  /fmonitoringsys/api/filesinfo} : restituisce le informazioni dei
	 * file che cadono nell'intervallo date/time di input per l'ultima modifica.
	 *
	 * @param LocalDateTime start.
	 * @param LocalDateTime end.
	 * @return {@link ResultTO} con stato {@code 200 (OK)}
	 */
	@GetMapping("/filesinfo")
	public ResultTO getFilesInfo(
			@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
			@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
		return service.getFilesInfoFromInterval(startDateTime, endDateTime);
	}

	/**
	 * {@code GET  /fmonitoringsys/api/file} : restituisce le informazioni del file
	 * che corrisponde al checksum MD5 di input.
	 *
	 * @param String md5 checksum.
	 * @return {@link ResultTO} con stato {@code 200 (OK)}
	 */
	@GetMapping("/file")
	public ResultTO getFileFromMD5(@RequestParam(name = "md5") String md5) {
		return service.getFilesInfoFromMd5(md5);
	}

}
