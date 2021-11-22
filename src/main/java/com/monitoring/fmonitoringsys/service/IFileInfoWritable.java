package com.monitoring.fmonitoringsys.service;

import com.monitoring.fmonitoringsys.to.InfoFileTO;

public  interface IFileInfoWritable {
    public boolean AppendFileInfo(InfoFileTO _fileInfo, String logFolderName);
}