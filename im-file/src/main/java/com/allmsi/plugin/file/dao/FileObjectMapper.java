package com.allmsi.plugin.file.dao;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.file.model.FilePo;
import com.allmsi.plugin.file.model.FileObjectPo;

public interface FileObjectMapper {

    FileObjectPo selectByPrimaryKey(String id);

    List<FilePo> selectByMD5(Map<String, Object> map);

	List<FilePo> selectByObject(Map<String, Object> map);

    int insert(FileObjectPo record);

	int deleteByFileId(String id);
}