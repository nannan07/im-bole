package com.allmsi.plugin.file.dao;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.file.model.FilePo;

public interface FileMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(FilePo record);

    FilePo selectByPrimaryKey(String id);

    List<FilePo> selectByMD5(Map<String, Object> map);

	int update(FilePo file);

	int deleteFlag(String id);

	List<FilePo> selectByIds(List<String> reseourcesId);

}