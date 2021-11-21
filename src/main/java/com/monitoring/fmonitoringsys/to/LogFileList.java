package com.monitoring.fmonitoringsys.to;

import java.util.ArrayList;
import java.util.List;

public class LogFileList {
    List<InfoFileTO> fileList = new ArrayList<>();
    public List<InfoFileTO> getLogFileList(){
        return fileList;
    }
    public void addLogFileList(InfoFileTO _file){
        if(!fileList.contains(_file)){
            this.fileList.add(_file);
        }
    }
}
