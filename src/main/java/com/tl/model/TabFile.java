/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.model;

public class TabFile {
	private String uuid;
	private String filename;
	private String filepath;
	private String smallfilepath;
	private String filesize;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public String toString() {
		return "TabFile [uuid=" + uuid + ", filename=" + filename + ", filepath=" + filepath + ", smallfilepath="
				+ smallfilepath + ", filesize=" + filesize + "]";
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getSmallfilepath() {
		return smallfilepath;
	}

	public void setSmallfilepath(String smallfilepath) {
		this.smallfilepath = smallfilepath;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
}
