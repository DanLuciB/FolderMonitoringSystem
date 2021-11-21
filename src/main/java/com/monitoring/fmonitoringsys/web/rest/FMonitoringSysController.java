package com.monitoring.fmonitoringsys.web.rest;

import java.time.LocalDateTime;

import com.monitoring.fmonitoringsys.service.FileMonitoringService;
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
		this.service = new FileMonitoringService();
	}

	/**
	 * {@code GET  /fmonitoringsys/api/filesinfo} : restituisce le informazioni dei
	 * lists all the file (one, more or no one) which have their last change datetime falling in the input datetime range
	 *
	 * @param LocalDateTime start.
	 * @param LocalDateTime end.
	 * @return {@link ResultTO} with status {@code 200 (OK)}
	 */
	@GetMapping("/filesinfo")
	public ResultTO getFilesInfo(
			@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
			@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
		return service.getFileInfoFromInterval(startDateTime, endDateTime);
	}

	/**
	 * {@code GET  /fmonitoringsys/api/file} :
	 * gives in output the file (one or no one) info of the file with the specific hash MD5 string parameter
	 *
	 * @param String md5 checksum.
	 * @return {@link ResultTO} con stato {@code 200 (OK)}
	 */
	@GetMapping("/file")
	public ResultTO getFileFromMD5(@RequestParam(name = "md5") String md5) {
		return service.getFileInfoFromMd5(md5);
	}

}
