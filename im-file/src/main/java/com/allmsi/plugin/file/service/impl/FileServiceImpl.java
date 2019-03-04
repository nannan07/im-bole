package com.allmsi.plugin.file.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allmsi.plugin.file.dao.FileMapper;
import com.allmsi.plugin.file.dao.FileObjectMapper;
import com.allmsi.plugin.file.model.FilePo;
import com.allmsi.plugin.file.model.FileObjectPo;
import com.allmsi.plugin.file.model.FileVo;
import com.allmsi.plugin.file.service.FileService;
import com.allmsi.sys.config.PropertyConfig;
import com.allmsi.sys.util.FileUtil;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class FileServiceImpl implements FileService {

	@Resource
	private FileMapper fileDao;

	@Resource
	private FileObjectMapper fileObjectDao;

	@Autowired
	private PropertyConfig properties;

	private final String UPLOAD_FILE_PATH = "upload_file_path";

	private final String CHUNKED_TEMP_FILE_PATH = "chunked_temp_path";

	private final String DOWDLOAD_URL = "download_url";

	private final String FILE_MD5_UNIQUE = "file_md5_unique";

	private final String FILE_PATH_DATE_FORMAT = "yyyyMM/dd";
	
	private final String ABSOLUTE_PATH_TYPE = "11";

	@Override
	public FileVo upload(MultipartFile file) {
		if (file == null) {
			return null;
		}
		String fileName = file.getOriginalFilename();// 文件原始名称(上传前的名称)
		String fileType = FileUtil.getExt(fileName);// 文件类型(后缀)
		String fileId = UUIDUtil.getUUID();
		String nFileName = fileId;// 新文件名称
		long fileSize = file.getSize();
		String sqlFilePath = "/" + new SimpleDateFormat(FILE_PATH_DATE_FORMAT).format(new Date());
		String filePath = properties.getProperty(UPLOAD_FILE_PATH) + sqlFilePath;
		FileUtil.mkdir(filePath);// 创建目录
		File resultFile = new File(filePath + "/" + nFileName + fileType);// 创建目标文件
		FileVo resultFileVo = new FileVo();
		try {
			file.transferTo(resultFile);// 写入磁盘
		} catch (IllegalStateException | IOException e) {
			return null;
		}
		FilePo record = new FilePo();
		record.setId(fileId);
		record.setFileName(fileName);
		record.setnFileName(nFileName);
		record.setFileType(fileType);
		record.setFilePath(sqlFilePath);
		record.setFilePathType(ABSOLUTE_PATH_TYPE);
		record.setFileSize(fileSize);
		record.setFileStatus(1);
		record.setDel(0);
		fileDao.insert(record);
		resultFileVo.setId(record.getId());
		resultFileVo.setName(record.getFileName());
		resultFileVo.setUrl(properties.getProperty(DOWDLOAD_URL) + record.getId());
		resultFileVo.setLength(record.getFileSize());
		return resultFileVo;
	}

	@Override
	public FileVo upload(MultipartFile file, String objectId, String objectType) {
		if (StrUtil.isEmpty(objectId)) {
			return null;
		}
		FileVo filevo = upload(file);
		if (file == null || StrUtil.isEmpty(filevo.getId())) {
			return null;
		}

		FileObjectPo fileObject = new FileObjectPo();
		fileObject.setId(UUIDUtil.getUUID());
		fileObject.setFileId(filevo.getId());
		fileObject.setObjectId(objectId);
		fileObject.setObjectType(objectType);
		fileObjectDao.insert(fileObject);
		filevo.setType(objectType);
		return filevo;
	}

	@Override
	public FileVo chunkedInput(String fileName, String fileMD5, long fileSize) {
		return chunkedInput(fileName, fileMD5, fileSize, null, null);
	}

	@Override
	public FileVo chunkedInput(String fileName, String fileMD5, long fileSize, String objectId, String objectType) {
		if (StrUtil.isEmpty(fileName) || StrUtil.isEmpty(fileMD5)) {
			return null;
		}

		FileVo resultFileVo = new FileVo();
		String fileType = FileUtil.getExt(fileName);// 文件类型(后缀)
		String fileId = UUIDUtil.getUUID();
		String nFileName = fileId;// 新文件名称
		String sqlFilePath = "";
		int fileStatus = 0;

		boolean isFileMD5Unique = Boolean.valueOf(properties.getProperty(FILE_MD5_UNIQUE));
		List<FilePo> fileInfoList4Status0 = new ArrayList<FilePo>();
		if (StrUtil.notEmpty(objectId)) {
			fileInfoList4Status0 = selectByMD5(fileMD5, 0, objectId);
		}
		List<FilePo> fileInfoList4Status1 = selectByMD5(fileMD5, 1);
		if (fileInfoList4Status1 != null && fileInfoList4Status1.size() > 0) {
			if (isFileMD5Unique) {
				resultFileVo.setStatus(1);
				if (fileInfoList4Status0 != null && fileInfoList4Status0.size() > 0) {
					for (FilePo fileBo : fileInfoList4Status0) {
						delete(fileBo.getId());
					}
				}
				return resultFileVo;
			} else {
				FilePo fileInfo = fileInfoList4Status1.get(0);
				nFileName = fileInfo.getnFileName();
				fileType = fileInfo.getFileType();
				sqlFilePath = fileInfo.getFilePath();
				fileStatus = 1;
			}
		} else {
			sqlFilePath = "/" + new SimpleDateFormat(FILE_PATH_DATE_FORMAT).format(new Date());
		}
		if (fileInfoList4Status0 != null && fileInfoList4Status0.size() > 0) {
			FilePo fileBo = fileInfoList4Status0.get(0);
			resultFileVo.setId(fileBo.getId());
			resultFileVo.setName(fileBo.getFileName());
			resultFileVo.setStatus(fileBo.getFileStatus());
			resultFileVo.setUrl(properties.getProperty(DOWDLOAD_URL) + fileBo.getId());
			resultFileVo.setLength(fileBo.getFileSize());
			fileBo.setFileName(fileName);
			fileBo.setFileType(fileType);
			fileBo.setFileSize(fileSize);
			fileBo.setFileStatus(0);
			update(fileBo);
			return resultFileVo;
		}
		FilePo record = new FilePo();
		record.setId(fileId);
		record.setFileName(fileName);
		record.setnFileName(nFileName);
		record.setFileType(fileType);
		record.setFilePath(sqlFilePath);
		record.setFilePathType(ABSOLUTE_PATH_TYPE);
		record.setFileSize(fileSize);
		record.setFileMD5(fileMD5);
		record.setFileStatus(fileStatus);
		record.setDel(0);
		fileDao.insert(record);
		resultFileVo.setId(record.getId());
		resultFileVo.setName(record.getFileName());
		resultFileVo.setStatus(record.getFileStatus());
		resultFileVo.setUrl(properties.getProperty(DOWDLOAD_URL) + record.getId());
		resultFileVo.setLength(record.getFileSize());

		if (resultFileVo == null || StrUtil.isEmpty(resultFileVo.getId()) || StrUtil.isEmpty(objectId)) {
			return null;
		}

		FileObjectPo fileObject = new FileObjectPo();
		fileObject.setId(UUIDUtil.getUUID());
		fileObject.setFileId(resultFileVo.getId());
		fileObject.setObjectId(objectId);
		fileObject.setObjectType(objectType);
		fileObjectDao.insert(fileObject);
		return resultFileVo;
	}

	@Override
	public boolean chunkedUpload(MultipartFile file, String fileMD5, String chunk) {
		File filePath = new File(properties.getProperty(UPLOAD_FILE_PATH)
				+ properties.getProperty(CHUNKED_TEMP_FILE_PATH) + "/" + fileMD5);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		File chunkFile = new File(filePath.getAbsolutePath() + "/" + chunk);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), chunkFile);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkChunk(String fileMD5, String chunk, int chunkSize) {
		File checkFile = new File(properties.getProperty(UPLOAD_FILE_PATH)
				+ properties.getProperty(CHUNKED_TEMP_FILE_PATH) + "/" + fileMD5 + "/" + chunk);
		// 检查文件是否存在，且大小是否一致
		if (checkFile.exists() && checkFile.length() == chunkSize) {
			// 上传过
			return false;
		} else {
			// 没有上传过
			return true;
		}
	}

	@Override
	public FileVo mergeChunks(String fileId, String fileMD5) {
		FileVo resultFileVo = new FileVo();
		if (StrUtil.isEmpty(fileMD5)) {
			return null;
		}

		String chunksPath = properties.getProperty(UPLOAD_FILE_PATH) + properties.getProperty(CHUNKED_TEMP_FILE_PATH)
				+ "/" + fileMD5;
		// 合并文件
		// 需要合并的文件的目录标记
		// 读取目录里的所有文件
		File f = new File(chunksPath);
		File[] fileArray = f.listFiles(new FileFilter() {
			// 排除目录只要文件
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory() || !StrUtil.isNumeric(pathname.getName())) {
					return false;
				}
				return true;
			}
		});

		// 转成集合，便于排序
		List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
					return -1;
				}
				return 1;
			}
		});

		FilePo fileInfo = selectById(fileId);
		String filePath = properties.getProperty(UPLOAD_FILE_PATH) + fileInfo.getFilePath();
		FileUtil.mkdir(filePath);// 创建目录
		File outputFile = new File(filePath + "/" + fileInfo.getnFileName() + fileInfo.getFileType());
		// 创建文件
		FileOutputStream outputStream = null;
		FileChannel outChnnel = null;
		try {
			outputFile.createNewFile();
			outputStream = new FileOutputStream(outputFile);
			// 输出流
			outChnnel = outputStream.getChannel();
			// 合并
			FileChannel inChannel;
			for (File file : fileList) {
				inChannel = new FileInputStream(file).getChannel();
				inChannel.transferTo(0, inChannel.size(), outChnnel);
				inChannel.close();
				// 删除分片
				file.delete();
			}
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (outChnnel != null) {
					outChnnel.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
			}
		}
		// 清除文件夹
		File tempFile = new File(chunksPath);
		if (tempFile.isDirectory() && tempFile.exists()) {
			tempFile.delete();
		}

		boolean isFileMD5Unique = Boolean.valueOf(properties.getProperty(FILE_MD5_UNIQUE));
		if (isFileMD5Unique) {
			if (selectByMD5(fileMD5, 1).size() > 0) {
				return null;
			} else {
				updateStatus(fileId, 1);
				List<FilePo> fileBoList = selectByMD5(fileMD5, 0);
				for (FilePo fileBo : fileBoList) {
					delete(fileBo.getId());
				}
			}
		} else {
			List<FilePo> fileBoList = selectByMD5(fileMD5, 0);
			for (FilePo fileBo : fileBoList) {
				fileBo.setnFileName(fileInfo.getnFileName());
				fileBo.setFileType(fileInfo.getFileType());
				fileBo.setFilePath(fileInfo.getFilePath());
				fileBo.setFileStatus(1);
				update(fileBo);
			}
		}
		resultFileVo.setId(fileInfo.getId());
		resultFileVo.setName(fileInfo.getFileName());
		resultFileVo.setUrl(properties.getProperty(DOWDLOAD_URL) + fileInfo.getId());
		resultFileVo.setLength(fileInfo.getFileSize());
		return resultFileVo;
	}

	@Override
	public FilePo selectById(String id) {
		return fileDao.selectByPrimaryKey(id);
	}

	private List<FilePo> selectByMD5(String fileMD5, int status) {
		if (StrUtil.isEmpty(fileMD5)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileMD5", fileMD5);
		map.put("fileStatus", status);
		return fileDao.selectByMD5(map);
	}

	private List<FilePo> selectByMD5(String fileMD5, int status, String objectId) {
		if (StrUtil.isEmpty(fileMD5) || StrUtil.isEmpty(objectId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileMD5", fileMD5);
		map.put("fileStatus", status);
		map.put("objectId", objectId);
		return fileObjectDao.selectByMD5(map);
	}

	@Override
	public List<FilePo> selectByObject(String objectId) {
		return selectByObject(objectId, null);
	}

	@Override
	public List<FilePo> selectByObject(String objectId, String objectType) {
		if (StrUtil.isEmpty(objectId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("objectType", objectType);
		List<FilePo> list = fileObjectDao.selectByObject(map);
		return list;
	}

	private boolean updateStatus(String id, int status) {
		if (StrUtil.isEmpty(id)) {
			return false;
		}
		FilePo file = new FilePo();
		file.setId(id);
		file.setFileStatus(status);
		return update(file);
	}

	private boolean update(FilePo fileBo) {
		if (fileBo == null || StrUtil.isEmpty(fileBo.getId())) {
			return false;
		}
		int msg = fileDao.update(fileBo);
		return (msg == 0) ? false : true;
	}

	@Override
	public boolean delete(String id) {
		if (StrUtil.isEmpty(id)) {
			return false;
		}
		FilePo fileInfo = fileDao.selectByPrimaryKey(id);
		if (fileInfo == null) {
			return false;
		}
		String path = properties.getProperty(UPLOAD_FILE_PATH) + fileInfo.getFilePath() + "/" + fileInfo.getnFileName()
		+ fileInfo.getFileType();
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		fileDao.deleteFlag(id);
		fileObjectDao.deleteByFileId(id);
		return true;
	}
	
	@Override
	public List<FilePo> selectByIds(List<String> reseourcesId) {
		return fileDao.selectByIds(reseourcesId);
	}

}