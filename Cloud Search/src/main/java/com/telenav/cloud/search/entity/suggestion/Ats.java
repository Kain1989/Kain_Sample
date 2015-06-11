
package com.telenav.cloud.search.entity.suggestion;

import java.util.List;

public class Ats{
   	private String code;
   	private String message;
   	private String result;
   	private String timestamp;
   	private List<Tip> tip_list;
   	private String total;
   	private String version;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Tip> getTip_list() {
		return tip_list;
	}

	public void setTip_list(List<Tip> tip_list) {
		this.tip_list = tip_list;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
