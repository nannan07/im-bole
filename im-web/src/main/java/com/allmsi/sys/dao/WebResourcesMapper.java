package com.allmsi.sys.dao;

import java.util.List;

import com.allmsi.sys.model.po.WebRes;

public interface WebResourcesMapper {

	/**
	 * 查询需要存入缓存中的资源信息
	 * @return
	 */
	List<WebRes> selectResListForCacheAdd();

}
