package com.monitoring.fmonitoringsys.service;

import java.time.LocalDateTime;

import com.monitoring.fmonitoringsys.to.ResultTO;

public interface IFileMonitoring {
    public void startsFileLocalMonitoring();   
    public ResultTO getFileInfoFromInterval(LocalDateTime startDateTime, LocalDateTime endDateTime);
    public ResultTO getFileInfoFromMd5(String md5String);
  }