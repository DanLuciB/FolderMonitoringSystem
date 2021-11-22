package com.monitoring.fmonitoringsys.service;
import java.io.*;
import java.net.URL;
import com.google.gson.*;
import com.monitoring.fmonitoringsys.to.InfoFileTO;

public class FileInfoJsonWrite implements IFileInfoWritable {
    @Override
    public boolean AppendFileInfo(InfoFileTO _fileInfo, String logFolderName){
        URL folderUrl = getFolderUrl(logFolderName);
        String logFilePath = folderUrl.getFile();
        Gson gson = new Gson();
        String jsonRepresentation = gson.toJson(_fileInfo);
        try {
            FileWriter Filewriter = new FileWriter(logFilePath, true);
            Filewriter.write(jsonRepresentation);
            Filewriter.flush();
            Filewriter.close();
            System.out.println("New File Added: " + _fileInfo.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to write file info into log file");
            return false;
        }
    }
    
    private URL getFolderUrl(String jsonFilePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResource(jsonFilePath);
	}
}
