package com.allmsi.sys.dao;

import java.util.List;

import com.allmsi.sys.model.po.WebMenu;

public interface WebMenuMapper {

	/**
	 * 查询需要存入缓存中的菜单信息
	 * @return
	 */
	List<WebMenu> selectMenuListForCacheAdd();

	/**
	 * 根据权限类型查询可以访问的菜单信息
	 * @param ids
	 * @return
	 */
	List<WebMenu> selectMenuByAuthDealIds(List<String> ids);
}
