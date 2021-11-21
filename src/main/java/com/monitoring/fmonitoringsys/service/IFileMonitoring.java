package com.monitoring.fmonitoringsys.service;

import java.time.LocalDateTime;

import com.monitoring.fmonitoringsys.to.ResultTO;

public interface IFileMonitoring {
    public void startFilesNewMonitoring();   
    public ResultTO getFilesInfoFromInterval(LocalDateTime startDateTime, LocalDateTime endDateTime);
    public ResultTO getFilesInfoFromMd5(String md5String);
  }