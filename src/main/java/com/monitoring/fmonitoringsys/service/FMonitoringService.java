package com.monitoring.fmonitoringsys.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.monitoring.fmonitoringsys.to.InfoFileTO;
import com.monitoring.fmonitoringsys.to.ResultTO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FMonitoringService implements IFileMonitoring {
	private static final String MONITORING_FOLDER_NAME = "checkFolder";
	private static final String LOGFileName = "logs/logFile.json";
	private static final long SheduleInterval = 3 * 1000;
	
	private static IFileWrite fileWriter;
	public FMonitoringService(){
		fileWriter = new JsonFileWritable();
	}

	@Override
	public void startFilesNewMonitoring() {
		URL folderUrl = this.getFolderUrl();
		String folderPath = folderUrl.getPath();
		File file = new File(folderPath);
		try {
			startFileNewMonitorExe(file);
		} catch (Exception e) {
			System.out.println("ERROR - Monitoring Shedule: " + e.getMessage());
		}
	}

	public static void startFileNewMonitorExe(File folder) throws Exception {
		FileAlterationObserver observer = new FileAlterationObserver(folder);
		FileAlterationMonitor monitor = new FileAlterationMonitor(SheduleInterval);
		FileAlterationListener fal = new FileAlterationListenerAdaptor() {
			@Override
			public void onFileCreate(File file) {
				InfoFileTO fileInfo = getSingleFileInfo(file);
				fileWriter.AppendFile(fileInfo,LOGFileName);				
			}
		};
		observer.addListener(fal);
		monitor.addObserver(observer);
		monitor.start();
	}

	/**
	 * Filters by datetime range the infoFile array obtained from the private method
	 * getAllFiles(). Give as output only the files which have as last change
	 * datetime in the given range.
	 **/
	@Override
	public ResultTO getFilesInfoFromInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		ResultTO result = this.getAllFiles();
		if (result.isError()) {
			return result;
		}
		List<InfoFileTO> infoFileList = result.getFiles();
		List<InfoFileTO> filterFileList = new ArrayList<>();
		// If it is void (when some error occurred) it is not executed
		for (InfoFileTO infoFileTO : infoFileList) {
			LocalDateTime lastModify = infoFileTO.getLastModify();
			if (lastModify.isAfter(startDateTime) && lastModify.isBefore(endDateTime)) {
				filterFileList.add(infoFileTO);
			}
		}
		result.setFiles(filterFileList);
		return result;
	}

	/**
	 * Search in the file list obtained from the private method getAllFiles() only
	 * the file which has that specific Hash MD5 given as input parameter
	 */
	@Override
	public ResultTO getFilesInfoFromMd5(String md5String) {
		ResultTO result = this.getAllFiles();
		if (result.isError()) {
			result.setMessage("Some error occurred in getting the file for the given MD5");
			return result;
		}
		List<InfoFileTO> infoFileList = result.getFiles();
		// If it is void (when some error occurred) it is not executed
		for (InfoFileTO infoFileTO : infoFileList) {
			if (infoFileTO.getHashMd5().equals(md5String)) {
				result.setMessage("Monitor Operation successfully completed");
				result.setFiles(Arrays.asList(infoFileTO));
				break;
			} else {
				result.setMessage("No file has been found with the given Hash MD5!");
				result.setFiles(new ArrayList<InfoFileTO>());
			}
		}
		return result;
	}

	private ResultTO getAllFiles() {
		URL folderUrl = this.getFolderUrl();
		ResultTO result = new ResultTO(
				"The folder " + MONITORING_FOLDER_NAME + " to monitor does not exists or there are no file inside!",
				new ArrayList<InfoFileTO>(), true);
		if (folderUrl == null) {
			return result;
		}
		result.setError(false);
		String folderPath = folderUrl.getPath();
		result = this.getFilesInfoFromFolder(folderPath);
		return result;
	}

	private static InfoFileTO getSingleFileInfo(File _file) {
		InfoFileTO infoFile = new InfoFileTO();
		String fileName = null;
		try {
			fileName = _file.getName();
			infoFile.setName(fileName);
			infoFile.setPath(_file.getAbsolutePath());
			Path infoFilePath = Paths.get(infoFile.getPath());
			long byteSize = Files.size(infoFilePath);
			infoFile.setByteSize(byteSize);
			long epoch = _file.lastModified();
			LocalDateTime lastModify = Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
			infoFile.setLastModify(lastModify);
			// Compute the Hash MD5 with the codec of Apache
			InputStream is = Files.newInputStream(infoFilePath);
			infoFile.setHashMd5(DigestUtils.md5Hex(is));
		} catch (IOException e) {
			System.out.println("Some error occurred analyzing the file: " + _file.getName() + e.getMessage());
		}
		return infoFile;
	}

	private ResultTO getFilesInfoFromFolder(String folderPath) {
		List<InfoFileTO> infoFileList = new ArrayList<>();
		ResultTO result = new ResultTO(
				"The folder " + MONITORING_FOLDER_NAME + " to monitor does not exists or there are no file inside!",
				infoFileList, true);

		File folderFile = new File(folderPath);

		boolean containsFile = false;
		for (final File fileEntry : folderFile.listFiles()) {
			if (!fileEntry.isDirectory()) {
				InfoFileTO infoFile = getSingleFileInfo(fileEntry);
				infoFileList.add(infoFile);
				containsFile = true;
			}
		}
		if (!containsFile) {
			return result;
		}
		result.setError(false);
		result.setMessage("Monitor Operation successfully completed");
		return result;
	}

	private URL getFolderUrl() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResource(MONITORING_FOLDER_NAME);
	}
}
