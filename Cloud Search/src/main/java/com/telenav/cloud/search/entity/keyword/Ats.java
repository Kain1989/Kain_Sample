
package com.telenav.cloud.search.entity.keyword;

import java.util.List;

public class Ats{
   	private String code;
   	private String message;
   	private List<Poi> poi_list;
   	private String result;
   	private String timestamp;
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

	public List<Poi> getPoi_list() {
		return poi_list;
	}

	public void setPoi_list(List<Poi> poi_list) {
		this.poi_list = poi_list;
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
