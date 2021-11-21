package com.monitoring.fmonitoringsys.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * POJO per mappare il risultato dell'invocazione delle api rest
 * 
 * @author dlucibello
 */
public class ResultTO {
	@JsonIgnore
	private boolean error;
	
	private String message;

	private List<InfoFileTO> files;

	public ResultTO() {
	}

	public ResultTO(String message) {
		this.message = message;
	}

	public ResultTO(String message, List<InfoFileTO> files) {
		this.message = message;
		this.files = files;
	}

	public ResultTO(String message, List<InfoFileTO> files, boolean error) {
		this.message = message;
		this.files = files;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<InfoFileTO> getFiles() {
		return files;
	}

	public void setFiles(List<InfoFileTO> files) {
		this.files = files;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ResultTO [message=" + message + ", files=" + files + "]";
	}
}
