package com.allmsi.plugin.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.allmsi.plugin.file.model.FilePo;
import com.allmsi.plugin.file.model.FileVo;

public interface FileService {

	FileVo upload(MultipartFile file);

	FileVo upload(MultipartFile file, String objectId, String objectType);

	FileVo chunkedInput(String fileName, String fileMd5, long fileSize);

	FileVo chunkedInput(String fileName, String fileMd5, long fileSize, String objectId, String objectType);
	
	boolean chunkedUpload(MultipartFile file, String fileMd5, String chunk);
	
	boolean checkChunk(String fileMd5, String chunk, int chunkSize);
	
	FileVo mergeChunks(String fileId, String fileMd5);
	
	FilePo selectById(String id);
	
	List<FilePo> selectByObject(String objectId);
	
	List<FilePo> selectByObject(String objectId, String objectType);
	
	boolean delete(String id);
	
	List<FilePo> selectByIds(List<String> reseourcesId);
	
}