package com.monitoring.fmonitoringsys.service;

import com.monitoring.fmonitoringsys.to.InfoFileTO;

public  interface IFileWrite {
    public boolean AppendFile(InfoFileTO _fileInfo, String logFolderName);
}