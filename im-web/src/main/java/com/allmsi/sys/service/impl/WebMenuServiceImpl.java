package com.allmsi.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.config.CacheInstence;
import com.allmsi.sys.dao.WebMenuMapper;
import com.allmsi.sys.model.po.WebMenu;
import com.allmsi.sys.model.vo.WebMenuVo;
import com.allmsi.sys.service.WebMenuService;

@Service
public class WebMenuServiceImpl implements WebMenuService {

	@Resource
	private WebMenuMapper webMenuDao;
	
	@Resource
	private CacheInstence cacheInstence;

	public void setMenuCache() {
		Map<String, List<WebMenuVo>> map = getRoleMenuMapForCache();
		for (Entry<String, List<WebMenuVo>> entry : map.entrySet()) {
			List<WebMenuVo> valueList = entry.getValue();
			if (valueList != null && valueList.size() > 0) {
				String key = entry.getKey();
				cacheInstence.setObject(CacheKeyPrefix.MENU.getValue() + key, valueList);
			}
		}
	}

	/**
	 * 查询角色对应的菜单信息
	 * @return
	 */
	private Map<String, List<WebMenuVo>> getRoleMenuMapForCache() {
		List<WebMenu> menuList = new ArrayList<WebMenu>();
		menuList.addAll(webMenuDao.selectMenuListForCacheAdd());
		Map<String, List<WebMenuVo>> map = new HashMap<String, List<WebMenuVo>>();
		for (WebMenu webMenu : menuList) {
			String roleId = webMenu.getAuthDealId();
			if (!map.containsKey(roleId)) {
				List<WebMenuVo> list = new ArrayList<WebMenuVo>();
				list.add(new WebMenuVo(webMenu));
				map.put(roleId, list);
			} else {
				List<WebMenuVo> list = new ArrayList<WebMenuVo>();
				list.addAll(map.get(roleId));
				list.add(new WebMenuVo(webMenu));
				map.put(roleId, list);
			}
		}
		return map;
	}
}
