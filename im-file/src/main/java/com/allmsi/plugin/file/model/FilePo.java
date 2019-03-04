package com.allmsi.plugin.file.model;

import java.util.Date;

public class FilePo {

	private String id;

	private String fileName;

	private String nFileName;

	private String fileType;

	private long fileSize;

	private String fileMD5;

	private String filePath;

	private int fileStatus;
	
	private String filePathType;

	private String cUserId;

	private String uUserId;

	private Date cTime;

	private Date uTime;

	private int del;

	private String objectId;

	private String objectType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getnFileName() {
		return nFileName;
	}

	public void setnFileName(String nFileName) {
		this.nFileName = nFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileMD5() {
		return fileMD5;
	}

	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(int fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFilePathType() {
		return filePathType;
	}

	public void setFilePathType(String filePathType) {
		this.filePathType = filePathType;
	}

	public String getcUserId() {
		return cUserId;
	}

	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}

	public String getuUserId() {
		return uUserId;
	}

	public void setuUserId(String uUserId) {
		this.uUserId = uUserId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}