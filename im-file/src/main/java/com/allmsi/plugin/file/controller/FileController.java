package com.allmsi.plugin.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.allmsi.plugin.file.model.FilePo;
import com.allmsi.plugin.file.model.FileVo;
import com.allmsi.plugin.file.service.FileService;
import com.allmsi.sys.config.PropertyConfig;
import com.allmsi.sys.model.protocol.ErrorProtocol;
import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.util.StrUtil;

@Controller
@RequestMapping("file")
public class FileController {

	@Resource
	private FileService fileService;
	
	@Autowired
	private PropertyConfig properties;

	private final String UPLOAD_FILE_PATH = "upload_file_path";

	private final String RELATIVE_PATH_TYPE = "01";

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(MultipartFile file, String objectId, String objectType) {
		FileVo fileInfo = fileService.upload(file, objectId, objectType);
		if (fileInfo == null || StrUtil.isEmpty(fileInfo.getId())) {
			return new ErrorProtocol("上传失败");
		}
		return new SuccessProtocol("上传成功", fileInfo);
	}

	@RequestMapping(value = "/chunked/input", method = RequestMethod.POST)
	@ResponseBody
	public Object chunkedInput(String fileName, String fileMD5, long fileSize, String objectId, String objectType) {
		if (StrUtil.isEmpty(objectId) || StrUtil.isEmpty(fileName) || StrUtil.isEmpty(fileMD5)) {
			return new ErrorProtocol("提交失败");
		}
		FileVo fileVo = fileService.chunkedInput(fileName, fileMD5, fileSize, objectId, objectType);
		return fileVo == null ? new ErrorProtocol("提交失败") : new SuccessProtocol(fileVo);
	}

	@RequestMapping(value = "/chunked/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object chunkedUpload(MultipartFile file, String fileMD5, String chunk) {
		if (StrUtil.isEmpty(fileMD5) || StrUtil.isEmpty(chunk)) {
			return new ErrorProtocol("上传失败");
		}
		return fileService.chunkedUpload(file, fileMD5, chunk) ? new SuccessProtocol(true) : new ErrorProtocol("上传失败");
	}

	@RequestMapping(value = "/chunked/checkChunk", method = RequestMethod.GET)
	@ResponseBody
	public Object checkChunk(String fileMD5, String chunk, int chunkSize) {
		if (StrUtil.isEmpty(fileMD5) || StrUtil.isEmpty(chunk) || chunkSize < 0) {
			return new ErrorProtocol("校验失败");
		}
		return new SuccessProtocol(fileService.checkChunk(fileMD5, chunk, chunkSize));
	}

	@RequestMapping(value = "/chunked/mergeChunks", method = RequestMethod.GET)
	@ResponseBody
	public Object mergeChunks(String fileId, String fileMD5) {
		if (StrUtil.isEmpty(fileId) || StrUtil.isEmpty(fileMD5)) {
			return new ErrorProtocol("合并失败");
		}
		FileVo fileVo = fileService.mergeChunks(fileId, fileMD5);
		return (fileVo == null || StrUtil.isEmpty(fileVo.getId())) ? new ErrorProtocol("合并失败", fileVo)
				: new SuccessProtocol(fileVo);
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public void download(String id, HttpServletResponse response, HttpServletRequest request) throws IOException {
		if (StrUtil.isEmpty(id)) {
			return;
		}
		FilePo fileInfo = fileService.selectById(id);
		if (fileInfo == null || StrUtil.isEmpty(fileInfo.getFilePath())) {
			return;
		}
		String path = fileInfo.getFilePath() + "/" + fileInfo.getnFileName() + fileInfo.getFileType();
		if (RELATIVE_PATH_TYPE.equals(fileInfo.getFilePathType())) {
			path = request.getSession().getServletContext().getRealPath("") + path;
		} else {
			path = properties.getProperty(UPLOAD_FILE_PATH) + path;
		}
		File file = new File(path);
		PrintWriter out = null;
		InputStream fis = null;
		OutputStream toClient = null;
		try {
			if (file.exists()) {
				fis = new BufferedInputStream(new FileInputStream(file));
				response.reset();
				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + new String(fileInfo.getFileName().getBytes("gb2312"), "iso-8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				byte[] buffer = new byte[1024 * 1024 * 4];
				int i = -1;
				while ((i = fis.read(buffer)) != -1) {
					toClient.write(buffer, 0, i);
				}
			} else {
				out = response.getWriter();
				out.print("<script>");
				out.print("alert(\"没有可下载文件！\")");
				out.print("</script>");
			}
		} catch (IOException ex) {
			out = response.getWriter();
			out.print("<script>");
			out.print("alert(\"没有可下载文件！\")");
			out.print("</script>");
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (toClient != null) {
				toClient.flush();
				toClient.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(String id) {
		if (StrUtil.isEmpty(id)) {
			return new ErrorProtocol("没有选择要删除的文件");
		}
		return new SuccessProtocol(fileService.delete(id));
	}
	
	@RequestMapping(value = "/objectId/select", method = RequestMethod.GET)
	@ResponseBody
	public Object objectIdSelect(String objectId, String objectType) {
		if (StrUtil.isEmpty(objectId)) {
			return new WarnProtocol();
		}
		return new SuccessProtocol(fileService.selectByObject(objectId, objectType));
	}

}