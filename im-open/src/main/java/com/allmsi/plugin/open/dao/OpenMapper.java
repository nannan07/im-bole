package com.allmsi.plugin.open.dao;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.open.model.OpenPo;

public interface OpenMapper {

    OpenPo select(Map<String, Object> map);

	int insertBatch(List<OpenPo> list);

	int updateByPrimaryKey(String id);
}